package com.libman.libmanweb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author manish
 *
 */
@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = {"UID", "ROLE"}))
public class Users {
	public static final String ROLE_LIBRARIAN = "LIBRARIAN";
    public static final String ROLE_USER = "USER";
    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE})
    List<UserBook> currentBooks = new ArrayList<UserBook>();
    // Added for search, add and update
    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE})
    List<LibUserBook> addUpdateList = new ArrayList<LibUserBook>();
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", length = 8, unique = true, nullable = false)
    private Long id;
    @Column(name = "UID", nullable = false)
    private Long uid;
    @Column(name = "USEREMAIL", nullable = false, unique = true)
    private String useremail;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "ROLE")
    private String role;
    @Column(name = "ENABLED")
    private boolean enabled;

    /**
     *
     */
    public Users() {
        this.enabled = false;
    }

    /**
     * @param useremail
     * @param password
     * @param role
     * @param enabled
     */
    public Users(String useremail, String password, String role, boolean enabled) {
        this.useremail = useremail;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    /**
     *
     * @param sjsuid
     * @param useremail
     * @param password
     * @param role
     * @param enabled
     */
    public Users(long uid, String useremail, String password, String role, boolean enabled) {
        this.uid = uid;
        this.useremail = useremail;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
    
    /**
    *
    * @return THe Library user books list
    */
   public List<LibUserBook> getAddUpdateList() {
       return addUpdateList;
   }

   /**
    *
    * @param addUpdateList
    */
   public void setAddUpdateList(List<LibUserBook> addUpdateList) {
       this.addUpdateList = addUpdateList;
   }

	public Long getId() {
		return id;
	}

	public Long getUid() {
		return uid;
	}

	public String getUseremail() {
		return useremail;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
     * @return the currentBooks
     */
    public List<UserBook> getCurrentBooks() {
        return currentBooks;
    }

    /**
     * @param currentBooks the currentBooks to set
     */
    public void setCurrentBooks(List<UserBook> currentBooks) {
        this.currentBooks = currentBooks;
    }

    /**
     *
     * @return The string representation of theuser object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid=" + uid +
                ", useremail='" + useremail + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
