package org.x98zy.webtask.controller;

import org.x98zy.webtask.service.advertisement.AdvertisementServiceImpl;
import org.x98zy.webtask.dao.AdvertisementDao;
import org.x98zy.webtask.model.Advertisement;
import org.x98zy.webtask.model.User;
import org.x98zy.webtask.exception.WebTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/ads/*")
public class AdvertisementController extends HttpServlet {
    private final AdvertisementServiceImpl advertisementService;
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    public AdvertisementController() {
        this.advertisementService = new AdvertisementServiceImpl(new AdvertisementDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = getAction(request);

        try {
            switch (action) {
                case "list":
                    showAdsList(request, response);
                    break;
                case "my":
                    showMyAds(request, response);
                    break;
                case "view":
                    showAdDetails(request, response);
                    break;
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (WebTaskException e) {
            logger.error("Error in advertisement controller", e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = getAction(request);

        try {
            switch (action) {
                case "create":
                    createAdvertisement(request, response);
                    break;
                case "edit":
                    updateAdvertisement(request, response);
                    break;
                case "delete":
                    deleteAdvertisement(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (WebTaskException e) {
            logger.error("Error in advertisement controller", e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return "list";
        }
        return pathInfo.substring(1); // убираем начальный "/"
    }

    private void showAdsList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WebTaskException {

        List<Advertisement> ads = advertisementService.getActiveAdvertisements();
        request.setAttribute("advertisements", ads);
        request.getRequestDispatcher("/WEB-INF/views/ads/list.jsp").forward(request, response);
    }

    private void showMyAds(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WebTaskException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Advertisement> ads = advertisementService.getUserAdvertisements(user);
        request.setAttribute("advertisements", ads);
        request.getRequestDispatcher("/WEB-INF/views/ads/my-ads.jsp").forward(request, response);
    }

    private void showAdDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WebTaskException {

        Long adId = Long.parseLong(request.getParameter("id"));
        Advertisement ad = advertisementService.getAdvertisementById(adId);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            boolean canEdit = advertisementService.canUserEditAdvertisement(ad, user);
            request.setAttribute("canEdit", canEdit);
        }

        request.setAttribute("ad", ad);
        request.getRequestDispatcher("/WEB-INF/views/ads/view.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/ads/create.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WebTaskException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long adId = Long.parseLong(request.getParameter("id"));
        Advertisement ad = advertisementService.getAdvertisementById(adId);

        if (!advertisementService.canUserEditAdvertisement(ad, user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setAttribute("ad", ad);
        request.getRequestDispatcher("/WEB-INF/views/ads/edit.jsp").forward(request, response);
    }

    private void createAdvertisement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WebTaskException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String city = request.getParameter("city");

        BigDecimal price = null;
        if (priceStr != null && !priceStr.trim().isEmpty()) {
            price = new BigDecimal(priceStr);
        }

        // Пока используем фиксированную категорию 1
        Long categoryId = 1L;

        Advertisement ad = advertisementService.createAdvertisement(
                title, description, price, city, categoryId, user, null
        );

        response.sendRedirect(request.getContextPath() + "/ads/view?id=" + ad.getId());
    }

    private void updateAdvertisement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WebTaskException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long adId = Long.parseLong(request.getParameter("id"));
        Advertisement ad = advertisementService.getAdvertisementById(adId);

        if (!advertisementService.canUserEditAdvertisement(ad, user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        ad.setTitle(request.getParameter("title"));
        ad.setDescription(request.getParameter("description"));

        String priceStr = request.getParameter("price");
        if (priceStr != null && !priceStr.trim().isEmpty()) {
            ad.setPrice(new BigDecimal(priceStr));
        } else {
            ad.setPrice(null);
        }

        ad.setCity(request.getParameter("city"));

        advertisementService.updateAdvertisement(ad, user);

        response.sendRedirect(request.getContextPath() + "/ads/view?id=" + ad.getId());
    }

    private void deleteAdvertisement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, WebTaskException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long adId = Long.parseLong(request.getParameter("id"));
        advertisementService.deleteAdvertisement(adId, user);

        response.sendRedirect(request.getContextPath() + "/ads/my");
    }
}