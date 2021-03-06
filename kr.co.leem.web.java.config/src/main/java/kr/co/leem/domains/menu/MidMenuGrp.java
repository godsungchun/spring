package kr.co.leem.domains.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015-01-15.
 */
@Entity(name = "MidMenuGrp")
@Table(name = "MID_MENU_GRP")
@JsonIgnoreProperties(value = {"topMenuGrp"})
public class MidMenuGrp {
	private Long midMenuGrpSeq;
	private Long tmgSeq;
	private TopMenuGrp topMenuGrp;
	private String name;
	private String url;
	private String description;
	private Boolean enabled;
	private String iconNm;
	private Integer ord;

	private List<LowMenu> lowMenu;
	private Date regDate;
	private String regId;
	private Date updDate;
	private String updId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "midMenuGrpSeq", length = 32)
	public Long getMidMenuGrpSeq() {
		return midMenuGrpSeq;
	}

	public void setMidMenuGrpSeq(Long midMenuGrpSeq) {
		this.midMenuGrpSeq = midMenuGrpSeq;
	}

	@Basic
	@Column(name = "tmgSeq", length = 32)
	public Long getTmgSeq() {
		return tmgSeq;
	}

	public void setTmgSeq(Long tmgSeq) {
		this.tmgSeq = tmgSeq;
	}

	@ManyToOne(targetEntity = TopMenuGrp.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "tmgSeq", referencedColumnName = "topMenuGrpSeq", insertable = false, updatable = false, foreignKey = @ForeignKey(name="FK_MidTmgSeq_TO_TmgSeq"))
	public TopMenuGrp getTopMenuGrp() {
		return topMenuGrp;
	}

	public void setTopMenuGrp(TopMenuGrp topMenuGrp) {
		this.topMenuGrp = topMenuGrp;
	}

	@Basic
	@Column(name = "name", length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "url", length = 255)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Basic
	@Column(name = "description", length = 120)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Basic
	@Column(name = "iconNm", length = 50)
	public String getIconNm() {
		return iconNm;
	}

	public void setIconNm(String iconNm) {
		this.iconNm = iconNm;
	}

	@Basic
	@Column(name = "ord", length = 10)
	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	@OneToMany(mappedBy = "midMenuGrp", fetch = FetchType.EAGER)
	public List<LowMenu> getLowMenu() {
		return lowMenu;
	}

	public void setLowMenu(List<LowMenu> lowMenu) {
		this.lowMenu = lowMenu;
	}

	@Basic
	@Column(name = "regDate", length = 20)
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Basic
	@Column(name = "regId", length = 32)
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	@Basic
	@Column(name = "updDate", length = 20)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Basic
	@Column(name = "updId", length = 32)
	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}
}