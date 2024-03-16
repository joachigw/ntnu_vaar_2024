package oving5_dockercompiler.model;

public class SourceCode {

    private String sourceCode;
    private String language;

    public SourceCode() {
    }
    public SourceCode(String sourceCode, String language) {
        this.sourceCode = sourceCode;
        this.language = language;
    }

    public String getSourceCode() {
        return sourceCode;
    }
    public String getLanguage() {
        return language;
    }
    public void setSourceCode(String newSourceCode) {
        this.sourceCode = newSourceCode;
    }
    public void setLanguage(String newLanguage) {
        this.language = newLanguage;
    }

}
