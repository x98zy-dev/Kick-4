package org.x98zy.webtask.command;

import org.x98zy.webtask.exception.WebTaskException;

public interface Command {
    void execute() throws WebTaskException;
}