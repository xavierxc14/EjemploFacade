<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>New Estudiante</title>
            <link rel="stylesheet" type="text/css" href="/Conexion/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Estudiante</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{estudiante.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Nombres:"/>
                    <h:inputText id="nombres" value="#{estudiante.estudiante.nombres}" title="Nombres" required="true" requiredMessage="The nombres field is required." />
                    <h:outputText value="Apellidos:"/>
                    <h:inputText id="apellidos" value="#{estudiante.estudiante.apellidos}" title="Apellidos" required="true" requiredMessage="The apellidos field is required." />
                    <h:outputText value="Cedula:"/>
                    <h:inputText id="cedula" value="#{estudiante.estudiante.cedula}" title="Cedula" required="true" requiredMessage="The cedula field is required." />
                    <h:outputText value="Direccion:"/>
                    <h:inputText id="direccion" value="#{estudiante.estudiante.direccion}" title="Direccion" required="true" requiredMessage="The direccion field is required." />

                </h:panelGrid>
                <br />
                <h:commandLink action="#{estudiante.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{estudiante.listSetup}" value="Show All Estudiante Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
