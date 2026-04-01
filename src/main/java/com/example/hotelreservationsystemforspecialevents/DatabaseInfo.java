package com.example.hotelreservationsystemforspecialevents;

import jakarta.persistence.*;

@Entity
@Table(name = "DatabaseInfo")
public class DatabaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "database_id")
    private Integer databaseId;

    @Column(name = "database_name")
    private String databaseName;

    @Column(name = "access_level")
    private String accessLevel;

    // Getters and Setters
    public Integer getDatabaseId() { return databaseId; }
    public void setDatabaseId(Integer databaseId) { this.databaseId = databaseId; }

    public String getDatabaseName() { return databaseName; }
    public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }

    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
}
