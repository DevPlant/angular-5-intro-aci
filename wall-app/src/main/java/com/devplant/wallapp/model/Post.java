package com.devplant.wallapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

@Table(name = "posts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 10)
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date creationDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Account author;

    public String getAuthorName(){
        return author.getUsername();
    }
}
