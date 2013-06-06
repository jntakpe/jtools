package fr.joss.jtools.exception;

import fr.joss.jtools.util.dto.ResponseMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * Capte les exceptions courantes et les traduit.
 * Cette classe est éxécutée après
 *
 * @author jntakpe
 */
@Aspect
@Component
@Order(Integer.MIN_VALUE)
public class ExceptionTranslator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Méthode interceptant les exceptions lancées par la couche service/business
     *
     * @param joinPoint méthode initialement appelée (greffon)
     * @return L'objet normalement retourné par la méthode appelée
     * @throws Throwable rethrow les exceptions
     */
    @Around("execution(* fr.joss.jtools.service..*.*(..))")
    public Object catchServiceExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            if (e instanceof DataAccessException)
                throw resolveTechnicalException(e);
            else
                throw e;
        }
        return result;
    }

    /**
     * Intercepte les exceptions lancées par les méthodes AJAX de la couche web
     *
     * @param joinPoint méthode initialement appelée (greffon)
     * @return L'objet normalement retourné par la méthode appelée
     * @throws Throwable rethrow les exceptions
     */
    @Around("execution(* fr.joss.jtools.web..*.*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Object catchWebExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            if (isReturningResponseMessage((MethodSignature) joinPoint.getSignature())) {
                if (e.getClass().isAssignableFrom(FrameworkException.class))
                    return ResponseMessage.getErrorMessage(e.getMessage());
                else
                    return ResponseMessage.getErrorMessage("Une erreur inconnue est survenue.");
            }
            throw e;
        }
        return result;
    }

    /**
     * Méthode a implémenter pour la traduction d'exception techniques
     *
     * @param e exception initialement lancée par la méthode appelée
     * @return l'exception traduite si possible sinon l'exception initiale
     */
    private Exception resolveTechnicalException(Exception e) {
        for (TechCode techCode : TechCode.values()) {
            if (e.getClass().isAssignableFrom(techCode.getSourceException()))
                return new TechException(techCode.getMessage(), techCode, e);
        }
        return e;
    }

    /**
     * Méthode indiquant si le greffon retourne un {@link ResponseMessage}
     *
     * @return true si le return est bien un {@link ResponseMessage}
     */
    private boolean isReturningResponseMessage(MethodSignature signature) {
        return signature.getReturnType().equals(ResponseMessage.class);
    }
}
