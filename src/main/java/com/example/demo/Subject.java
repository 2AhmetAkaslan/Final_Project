package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private boolean active;

    private int totalHours;
    
    private Long nStudents = 0L;
    
    private int nApproved = 0;

	public int getnApproved() {
		return nApproved;
	}

	public void setnApproved(int a) {
		this.nApproved = a;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(int totalHours) {
		this.totalHours = totalHours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getnStudents() {
		return nStudents;
	}

	public void setnStudents(Long nStudents) {
		this.nStudents = nStudents;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", active=" + active + ", totalHours=" + totalHours
				+ ", nStudents=" + nStudents + "]";
	}

	public void incrementNStudents() {
        this.nStudents++;
    }
	
	public void decrementNStudents() {
        this.nStudents--;
    }
}
