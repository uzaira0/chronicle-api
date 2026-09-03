package com.openlattice.chronicle.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.olingo.commons.api.edm.FullQualifiedName;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * describes chronicle_user_apps entity and chronicle_used_by associations
 */

public class ChronicleAppsUsageDetails {
    private final Map<FullQualifiedName, Set<Object>> entityDetails;
    private final Map<FullQualifiedName, Set<Object>> associationDetails;

    @JsonCreator
    public ChronicleAppsUsageDetails(
            @JsonProperty( SerializationConstants.ENTITY_DETAILS ) Map<FullQualifiedName, Set<Object>> entityDetails,
            @JsonProperty( SerializationConstants.ASSOCIATION_DETAILS )
                    Map<FullQualifiedName, Set<Object>> associationDetails
    ) {
        this.entityDetails = entityDetails;
        this.associationDetails = associationDetails;
    }

    @JsonProperty( SerializationConstants.ENTITY_DETAILS )
    public Map<FullQualifiedName, Set<Object>> getEntityDetails() {
        return entityDetails;
    }

    @JsonProperty( SerializationConstants.ASSOCIATION_DETAILS )
    public Map<FullQualifiedName, Set<Object>> getAssociationDetails() {
        return associationDetails;
    }

    @Override public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( !( o instanceof ChronicleAppsUsageDetails ) )
            return false;
        ChronicleAppsUsageDetails chronicleAppsUsageDetails = (ChronicleAppsUsageDetails) o;
        return Objects.equals( entityDetails, chronicleAppsUsageDetails.entityDetails ) &&
                Objects.equals( associationDetails, chronicleAppsUsageDetails.associationDetails );
    }

    @Override public String toString() {
        return "ChronicleAppsUsageDetails{" +
                "entityDetails=" + entityDetails +
                ", associationDetails=" + associationDetails +
                '}';
    }

    @Override public int hashCode() {
        return Objects.hash( entityDetails, associationDetails );
    }
}
