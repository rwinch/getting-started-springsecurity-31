package messages.security;

import java.io.Serializable;

import messages.data.Message;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class MessagePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if(targetDomainObject == null) {
            return true;
        }
        if(targetDomainObject instanceof Message) {
            return hasPermission(authentication, (Message)targetDomainObject, permission);
        }
        return false;
    }

    private boolean hasPermission(Authentication authentication, Message message, Object permission) {
        return message.getSummary().equals(authentication.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        throw new UnsupportedOperationException();
    }

}
