package ch.raiffeisen.ipricer.fxdesigner.generator;

public class GeneratorException extends IllegalArgumentException {

    ErrorReport errorReport;

    public GeneratorException(ErrorReport errorReport) {
        this.errorReport = errorReport;
    }

    public ErrorReport getErrorReport() {
        return errorReport;
    }
}
