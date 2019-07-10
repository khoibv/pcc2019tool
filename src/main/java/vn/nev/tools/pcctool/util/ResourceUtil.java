package vn.nev.tools.pcctool.util;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtil {
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Autowired
    @Qualifier("labelSource")
    private MessageSource labelSource;

    @Autowired
    @Qualifier("validationMessages")
    private MessageSource validationMessages;

    /**
     * Constructor
     */
    private ResourceUtil() {
        // Avoid initialize
    }

    /**
     * Try to resolve the message. Treat as an error if the message can't be found.
     *
     * @param code   the code to lookup up, such as 'calculator.noRateSet'
     * @param args   an array of arguments that will be filled in for params within
     *               the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     *               or {@code null} if none.
     * @param locale the locale in which to do the lookup
     *
     * @return the resolved message
     * @throws NoSuchMessageException if the message wasn't found
     * @see java.text.MessageFormat
     */
    public String getMessage(String code, Locale locale, Object[] args) {
        return messageSource.getMessage(code, args, locale);
    }

    public String getMessage(String code, Object... args) {
        return getMessage(code, null, args);
    }

    /**
     * @param code
     *
     * @return
     */
    public String getMessage(String code) {
        return getMessage(code, null, null);
    }

    /**
     * Try to resolve the message. Treat as an error if the message can't be found.
     *
     * @param code   the code to lookup up, such as 'calculator.noRateSet'
     * @param args   an array of arguments that will be filled in for params within
     *               the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     *               or {@code null} if none.
     * @param locale the locale in which to do the lookup
     *
     * @return the resolved message
     * @throws NoSuchMessageException if the message wasn't found
     * @see java.text.MessageFormat
     */
    public String getLabel(String code, Locale locale, Object[] args) {
        return labelSource.getMessage(code, args, locale);
    }

    public String getLabel(String code, Object... args) {
        return getLabel(code, null, args);
    }

    /**
     * @param code
     *
     * @return
     */
    public String getLabel(String code) {
        return getLabel(code, null, null);
    }

    /**
     * @param code
     * @param defaultValue Default value if can not find label
     * @param args
     *
     * @return
     */
    public String getLabelWithDefault(String code, String defaultValue, Object... args) {
        return labelSource.getMessage(code, args, defaultValue, null);
    }

    public String getValidationMessages(String code, Locale locale, Object[] args) {
        return validationMessages.getMessage(code, args, locale);
    }

    public String getValidationMessages(String code) {
        return getValidationMessages(code, null, null);
    }

    public String getValidationMessages(String code, Object... args) {
        return getValidationMessages(code, null, args);
    }

}
