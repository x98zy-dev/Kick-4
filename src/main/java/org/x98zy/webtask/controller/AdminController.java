package org.x98zy.webtask.controller;

import org.x98zy.webtask.service.advertisement.AdvertisementServiceImpl;
import org.x98zy.webtask.dao.AdvertisementDao;
import org.x98zy.webtask.model.Advertisement;
import org.x98zy.webtask.model.User;
import org.x98zy.webtask.model.UserRole;
import org.x98zy.webtask.command.Command;
import org.x98zy.webtask.command.impl.ApproveAdvertisementCommand;
import org.x98zy.webtask.command.impl.RejectAdvertisementCommand;
import org.x98zy.webtask.command.impl.DeleteAdvertisementCommand;
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
import java.util.List;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
    private final AdvertisementServiceImpl advertisementService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController() {
        this.advertisementService = new AdvertisementServiceImpl(new AdvertisementDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = request.getPathInfo();
        if (path == null || path.equals("/") || path.equals("/moderation")) {
            showModerationPanel(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = request.getParameter("action");
        Long adId = Long.parseLong(request.getParameter("id"));

        try {
            Command command = createCommand(action, adId);
            command.execute();

            response.sendRedirect(request.getContextPath() + "/admin/moderation");

        } catch (WebTaskException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private Command createCommand(String action, Long adId) {
        if ("approve".equals(action)) {
            return new ApproveAdvertisementCommand(advertisementService, adId);
        } else if ("reject".equals(action)) {
            return new RejectAdvertisementCommand(advertisementService, adId);
        } else if ("delete".equals(action)) {
            return new DeleteAdvertisementCommand(advertisementService, adId);
        } else {
            throw new IllegalArgumentException("Unknown action: " + action);
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() == UserRole.ADMIN;
    }

    private void showModerationPanel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Advertisement> pendingAds = advertisementService.getPendingAdvertisements();
            request.setAttribute("pendingAdvertisements", pendingAds);
            request.getRequestDispatcher("/WEB-INF/views/admin/moderation.jsp").forward(request, response);
        } catch (WebTaskException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}