<%
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="../init.jsp" %>

<portlet:actionURL name="scan" var="scanURL" />

<aui:form action="<%= scanURL %>" enctype="multipart/form-data" method="post" name="fm">
    <aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
    <aui:input name="folderName" type="file" />

    <aui:button-row>
        <aui:button cssClass="btn-primary" type="submit" />
    </aui:button-row>
</aui:form>