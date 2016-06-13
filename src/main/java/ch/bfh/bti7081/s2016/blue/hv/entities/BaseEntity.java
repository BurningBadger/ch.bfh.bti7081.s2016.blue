package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * {@link BaseEntity} providing an auto-incrementing ID, created_at and
 * updated_at fields for entities.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3624890888887228585L;

    /**
     * The ID of this entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The created_at field for this entity.
     */
    @Temporal(value = TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * The updated_at field for this entity.
     */
    @Temporal(value = TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
	return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
    }
}
