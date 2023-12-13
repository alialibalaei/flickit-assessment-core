package org.flickit.assessment.kit.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessageKey {

    public static final String TRANSLATE_KIT_DSL_UNABLE_TO_PARSE_JSON = "translate-kit-dsl.unable.to.parse.json";

    public static final String FIND_MATURITY_LEVEL_ID_NOT_FOUND = "find-maturity-level.id.notFound";
    public static final String FIND_KIT_ID_NOT_FOUND = "find-kit.id.notFound";

    public static final String UPDATE_KIT_BY_DSL_KIT_ID_NOT_NULL = "update-kit-by-dsl.kitId.notNull";
    public static final String UPDATE_KIT_BY_DSL_DSL_CONTENT_NOT_NULL = "update-kit-by-dsl.dsl-content.notNull";

    public static final String UPDATE_KIT_BY_DSL_ADDITION_UNSUPPORTED = "update-kit-by-dsl.addition.unsupported";
    public static final String UPDATE_KIT_BY_DSL_DELETION_UNSUPPORTED = "update-kit-by-dsl.deletion.unsupported";

    public static final String UPDATE_KIT_BY_DSL_ANSWER_OPTION_NOT_FOUND= "update-kit-by-dsl.answer-option.notFound";

    public static final String GRANT_USER_ACCESS_TO_KIT_KIT_ID_NOT_NULL = "grant-user-access-to-kit.kitId.notNull";
    public static final String GRANT_USER_ACCESS_TO_KIT_USER_EMAIL_NOT_NULL = "grant-user-access-to-kit.userEmail.notNull";
    public static final String GRANT_USER_ACCESS_TO_KIT_CURRENT_USER_EMAIL_NOT_NULL = "grant-user-access-to-kit.currentUserEmail.notNull";
    public static final String GRANT_USER_ACCESS_TO_KIT_KIT_ID_NOT_FOUND = "grant-user-access-to-kit.kitId.notFound";
    public static final String GRANT_USER_ACCESS_TO_KIT_KIT_OWNER_NOT_FOUND = "grant-user-access-to-kit.kitOwner.notFound";
    public static final String GRANT_USER_ACCESS_TO_KIT_USER_EMAIL_NOT_FOUND = "grant-user-access-to-kit.userEmail.notFound";
    public static final String GRANT_USER_ACCESS_TO_KIT_USER_ALREADY_EXIST = "grant-user-access-to-kit.userEmail.alreadyExist";
    public static final String GRANT_USER_ACCESS_TO_KIT_CURRENT_USER_NOT_ALLOWED = "grant-user-access-to-kit.currentUser.notAllowed";

    public static String entityNameSingleFirst(String fieldName) {
        return String.format("entities.%s.single.first", fieldName);
    }

    public static String entityNamePlural(String fieldName) {
        return String.format("entities.%s.plural", fieldName);
    }

}
