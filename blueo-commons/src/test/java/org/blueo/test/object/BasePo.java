package org.blueo.test.object;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasePo {
	
	protected Long id;
    
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    final public Long getId() {
        return this.id;
    }

    final public void setId(Long id) {
        this.id = id;
    }
}
