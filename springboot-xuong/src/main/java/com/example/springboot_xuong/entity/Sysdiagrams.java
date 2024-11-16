package com.example.springboot_xuong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sysdiagrams")
public class Sysdiagrams {

    @Column(name = "name")
    private String name;

    @Column(name = "principal_id")
    private long principalId;

    @Id
    @Column(name = "diagram_id")
    private long diagramId;

    @Column(name = "version")
    private long version;

    @Column(name = "definition")
    private String definition;

}
