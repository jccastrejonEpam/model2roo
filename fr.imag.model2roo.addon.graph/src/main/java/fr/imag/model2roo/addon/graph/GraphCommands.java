/*
 * Copyright 2012 jccastrejon
 *  
 * This file is part of Model2Roo.
 *
 * Model2Roo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Model2Roo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Model2Roo.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.imag.model2roo.addon.graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.springframework.roo.classpath.converters.JavaTypeConverter;
import org.springframework.roo.classpath.details.BeanInfoUtils;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.shell.CliAvailabilityIndicator;
import org.springframework.roo.shell.CliCommand;
import org.springframework.roo.shell.CliOption;
import org.springframework.roo.shell.CommandMarker;

/**
 * Commands provided by the graph add-on.
 * 
 * @author jccastrejon
 * 
 */
@Component
@Service
public class GraphCommands implements CommandMarker {

    /**
     * Operations implemented by this add-on.
     */
    @Reference
    private GraphOperations operations;

    /**
     * Graph database provider.
     */
    GraphProvider currentProvider;

    /**
     * Determines if the setup command can be issued.
     * 
     * @return
     */
    @CliAvailabilityIndicator("graph setup")
    public boolean isGraphSetupAvailable() {
        return operations.isGraphSetupAvailable();
    }

    /**
     * Setup command.
     * 
     * @param graphProvider
     * @param databaseLocation
     */
    @CliCommand(value = "graph setup", help = "Install or updates a Graph database provider in your project")
    public void installGraphPersistence(
            @CliOption(key = { "provider" }, mandatory = true, help = "The graph provider to support") final GraphProvider graphProvider,
            @CliOption(key = { "databaseLocation" }, mandatory = true, help = "The database location to use") final String databaseLocation) {
        this.currentProvider = graphProvider;
        this.operations.graphSetup(graphProvider, databaseLocation);
    }

    /**
     * Determines if the entity command can be issued.
     * 
     * @return
     */
    @CliAvailabilityIndicator("entity graph")
    public boolean isGraphEntityAvailable() {
        return operations.isNewEntityAvailable();
    }

    /**
     * Entity command.
     * 
     * @param name
     * @param superClass
     * @param isAbstract
     */
    @CliCommand(value = "entity graph", help = "Creates a new graph entity in SRC_MAIN_JAVA")
    public void newGraphEntity(
            @CliOption(key = "class", optionContext = "update,project", mandatory = true, help = "Name of the entity to create") final JavaType name,
            @CliOption(key = "extends", mandatory = false, unspecifiedDefaultValue = "java.lang.Object", help = "The superclass (defaults to java.lang.Object)") final JavaType superClass,
            @CliOption(key = "abstract", mandatory = false, specifiedDefaultValue = "true", unspecifiedDefaultValue = "false", help = "Whether the generated class should be marked as abstract") final boolean isAbstract) {
        if (BeanInfoUtils.isEntityReasonablyNamed(name)) {
            operations.newEntity(name, superClass, isAbstract);
        } else {
            throw new IllegalArgumentException("Invalid entity name");
        }
    }

    /**
     * Determines if the relationship-entity command can be issued.
     * 
     * @return
     */
    @CliAvailabilityIndicator("relationshipEntity graph")
    public boolean isGraphRelationshipEntityAvailable() {
        return operations.isNewRelationshipEntityAvailable();
    }

    /**
     * Relationship-Entity command.
     * 
     * @param name
     * @param type
     * @param fromNode
     * @param toNode
     * @param properties
     */
    @CliCommand(value = "relationship entity graph", help = "Creates a new graph relationship entity in SRC_MAIN_JAVA")
    public void newGraphRelationshipEntity(
            @CliOption(key = "class", optionContext = "update,project", mandatory = true, help = "Name of the entity to create") final JavaType name,
            @CliOption(key = "type", mandatory = true, help = "Name of relationship") final String type,
            @CliOption(key = "from", optionContext = "update,project", mandatory = true, help = "Name of the start graph entity") final JavaType fromNode,
            @CliOption(key = "to", optionContext = "update,project", mandatory = true, help = "Name of the end graph entity") final JavaType toNode,
            @CliOption(key = "properties", mandatory = false, help = "List of properties, separated by commas") final String properties) {
        List<String> propertiesList;
        if (BeanInfoUtils.isEntityReasonablyNamed(name)) {
            propertiesList = null;
            if (properties != null) {
                propertiesList = new ArrayList<String>();
                for (String property : properties.split(",")) {
                    propertiesList.add(property.trim());
                }
            }
            operations.newRelationshipEntity(name, type, fromNode, toNode, propertiesList);
        } else {
            throw new IllegalArgumentException("Invalid entity name");
        }
    }

    /**
     * Determines if the repository command can be issued.
     * 
     * @return
     */
    @CliAvailabilityIndicator("repository graph")
    public boolean isGraphRepositoryAvailable() {
        return operations.isNewRepositoryAvailable();
    }

    /**
     * Repository command.
     * 
     * @param name
     * @param domainType
     */
    @CliCommand(value = "repository graph", help = "Creates a new graph repository in SRC_MAIN_JAVA")
    public void newGraphRepository(
            @CliOption(key = "interface", optionContext = "update,project", mandatory = true, help = "Name of the repository to create") final JavaType name,
            @CliOption(key = "entity", optionContext = JavaTypeConverter.PROJECT, mandatory = true, help = "The domain entity this repository should expose") final JavaType domainType) {
        operations.newRepository(name, domainType);
    }

    /**
     * Determines if the relationship command can be issued.
     * 
     * @return
     */
    @CliAvailabilityIndicator("relationship graph")
    public boolean isGraphRelationshipAvailable() {
        return operations.isNewRelationshipAvailable();
    }

    /**
     * Relationship command.
     * 
     * @param viaNode
     * @param fieldName
     * @param type
     * @param fromNode
     * @param toNode
     * @param direction
     * @param relationshipType
     */
    @CliCommand(value = "relationship graph", help = "Creates a new relationship between two graph entities")
    public void newGraphRelationship(
            @CliOption(key = "via", mandatory = false, help = "Name of explicit relationship class (Either this or the 'to' property has to be specified).") final JavaType viaNode,
            @CliOption(key = "fieldName", mandatory = true, help = "Name of the field name") final String fieldName,
            @CliOption(key = "type", mandatory = false, help = "Name of relationship") final String type,
            @CliOption(key = "from", optionContext = "update,project", mandatory = true, help = "Name of the start graph entity") final JavaType fromNode,
            @CliOption(key = "to", optionContext = "update,project", mandatory = false, help = "Name of the end graph entity (Either this or the 'via' property has to be specified). ") final JavaType toNode,
            @CliOption(key = "direction", mandatory = false, help = "INCOMING or OUTGOING") final Direction direction,
            @CliOption(key = "relationshipType", mandatory = false, unspecifiedDefaultValue = "SINGLE", specifiedDefaultValue = "SINGLE", help = "SINGLE, MODIFIABLE or READ_ONLY") final RelationshipType relationshipType) {
        boolean isVia;
        JavaType relationNode;

        // Determine if there's an associated relationshipEntity
        isVia = false;
        relationNode = toNode;
        if (viaNode != null) {
            relationNode = viaNode;
        }

        operations.newRelationship(fromNode, relationNode, isVia, type, direction, fieldName, relationshipType);
    }

    /**
     * Determines if the web mvc setup can be issued.
     * 
     * @return
     */
    @CliAvailabilityIndicator("web mvc setup graph")
    public boolean isMvcSetupAvailable() {
        return operations.isMvcSetupAvailable();
    }

    /**
     * Web mvc setup command.
     */
    @CliCommand(value = "web mvc setup graph", help = "Setups basic mvc support for graph entities")
    public void mvcSetup() {
        operations.mvcSetup();
    }
}