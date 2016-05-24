package com.ddd.poc.domain.core.command;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.exception.ExceptionUtils;

public class ReportApplicationErrorCommand extends DomainCommand {

    private final String exceptionClass;

    private final String trace;

    private final DomainCommand sourceCommand;

    private final String sourceCommandClass;

    public ReportApplicationErrorCommand(Throwable exception, DomainCommand sourceCommand) {
        Preconditions.checkNotNull(exception);
        Preconditions.checkNotNull(sourceCommand);

        this.exceptionClass = exception.getClass().getCanonicalName();
        this.trace = ExceptionUtils.getStackTrace(exception);
        this.sourceCommand = sourceCommand;
        this.sourceCommandClass = sourceCommand.getClass().getCanonicalName();
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public String getTrace() {
        return trace;
    }

    public DomainCommand getSourceCommand() {
        return sourceCommand;
    }

    public String getSourceCommandClass() {
        return sourceCommandClass;
    }
}
