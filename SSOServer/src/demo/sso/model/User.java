package demo.sso.model;

import java.io.Serializable;

/**
 * 用户实体类
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class User implements Serializable {

	private Integer id;
	private String name;
	private String account;
	private String passwd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
