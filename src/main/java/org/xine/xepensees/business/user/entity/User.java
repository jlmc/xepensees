package org.xine.xepensees.business.user.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.xine.xepensees.business.validators.control.NotBlank;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "t_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @Version
    @Column(name = "version")
    private int version;
    
    @NotBlank
    @Column(name = "email", length = 100, nullable = false)
	private String email;

    
    @NotNull
    @Column(name = "password", length = 512, nullable = false)
	private String password;
    
    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
	private String name;
    
    
    
    @ElementCollection
    @CollectionTable(name = "t_user_roles", joinColumns = { @JoinColumn(name = "userId") })
    private Set<Permission> permissions = EnumSet.noneOf(Permission.class);;
    
    protected User() {}
    
    public void addPermission(final Permission permission) {
    	this.permissions.add(permission);
	}

    public Set<Permission> getPermissions() {
		return Collections.unmodifiableSet(this.permissions);
	}
    
    protected void setPermissions(final Set<Permission> permissions) {
    	this.permissions.clear();
		this.permissions.addAll(permissions);
	}
    
    public Long getId() {
		return this.id;
	}
    
    public void setId(final Long id) {
		this.id = id;
	}
    
    public String getName() {
		return this.name;
	}
    
    public void setName(final String name) {
		this.name = name;
	}
    
    public String getPassword() {
		return this.password;
	}
    
    public void setPassword(final String password) {
		this.password = password;
	}
    
    public String getEmail() {
		return this.email;
	}
    
    public void setEmail(final String email) {
		this.email = email;
	}
    
    
    @Override
    public int hashCode() {
    	return Objects.hash(this.email);
    }
    
    @Override
    public boolean equals(final Object o) {
    	if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        final User user = (User) o;
        return Objects.equals(this.email, user.email);
    }
    
    @Override
   	public String toString() {
   		return "User [username=" + this.email + ", permissions=" + this.permissions + "]";
   	}
    
    protected int getVersion() {
		return this.version;
	}
    
    protected void setVersion(final int version) {
		this.version = version;
	}

	public static User empty() {
		return new User();
	}

}
