package sample.org.blueo.db.po;

import javax.persistence.Id;
import org.blueo.commons.jdbc.core.po.TraceablePo;
import org.blueo.commons.jdbc.core.po.HasId;
import javax.persistence.Column;
import java.util.Date;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PERSON")
@SuppressWarnings("serial")
public class TblPerson implements Serializable, TraceablePo<Long>, HasId<Long> {
	private Long id;
	private String name;
	private Integer age;
	private String sex;
	private Long createId;
	private Long updateId;
	private Date createTime;
	private Date updateTime;
	private Boolean delFlag;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AGE")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "CREATE_ID")
	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Column(name = "UPDATE_ID")
	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "DEL_FLAG")
	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TblPerson [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", age=");
		builder.append(age);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", createId=");
		builder.append(createId);
		builder.append(", updateId=");
		builder.append(updateId);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", delFlag=");
		builder.append(delFlag);
		builder.append("]");
		return builder.toString();
	}

}
