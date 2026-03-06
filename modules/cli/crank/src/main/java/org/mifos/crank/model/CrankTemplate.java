/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.crank.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CrankTemplate {
    private String templateId;
    private String groupId;
    private String description;
    private String author;
    private Map<String, CrankPrompt> prompts;
    private String layout;
    private Map<String, String> computed;
}
