package org.xine.xepensees.business.expenses.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.persistence.control.LocalDateConverter;
import org.xine.xepensees.business.users.entity.User;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "t_expense")
public class Expense implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@NotNull
	@DecimalMax(value = "9999")
	private BigDecimal amount = BigDecimal.ZERO;

	@NotNull
	private Currency currency = Currency.EURO;

	@NotNull
	private ExpenseType expenseType;

	@Size(max = 5)
	private String description;

	@NotNull
	@Convert(converter = LocalDateConverter.class)
	private LocalDate date;

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "conference_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_conference"))
	private Conference conference;

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user"))
	private User user;

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public Conference getConference() {
		return this.conference;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	protected int getVersion() {
		return this.version;
	}

	protected void setVersion(final int version) {
		this.version = version;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

	public ExpenseType getExpenseType() {
		return this.expenseType;
	}

	public void setExpenseType(final ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	@Override
	public String toString() {
		return "Extense [id=" + this.id + ", version=" + this.version + ", amount=" + this.amount + ", description=" + this.description
				+ ", date=" + this.date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Expense other = (Expense) obj;

		return Objects.equals(this.id, other.id);
	}

}
