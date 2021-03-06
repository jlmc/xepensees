package org.xine.xepensees.business.expense.entity;

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
import javax.xml.bind.annotation.XmlTransient;

import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.business.persistence.control.LocalDateConverter;
import org.xine.xepensees.business.reimbursement.entity.Reimbursement;
import org.xine.xepensees.business.user.entity.User;

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

	@Size(max = 100)
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

	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "reimbursement_id", nullable = true, foreignKey = @ForeignKey(name = "fk_expenses_reimbursement_id"))
	private Reimbursement reimbursement;

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public Conference getConference() {
		return conference;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	protected int getVersion() {
		return version;
	}

	protected void setVersion(final int version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(final ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public Reimbursement getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(Reimbursement reimbursement) {
		this.reimbursement = reimbursement;
	}

	@Override
	public String toString() {
		return "Extense [id=" + id + ", version=" + version + ", amount=" + amount + ", description=" + description
				+ ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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

		return Objects.equals(id, other.id);
	}

	public static class Builder {
		private final Expense expense = new Expense();

		public static Builder builder() {
			return new Builder();
		}

		public Builder date(LocalDate date) {
			expense.date = date;
			return this;
		}

		public Builder description(String description) {
			expense.description = description;
			return this;
		}

		public Builder type(ExpenseType type) {
			expense.expenseType = type;
			return this;
		}

		public Builder euro() {
			expense.currency = Currency.EURO;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			expense.amount = amount;
			return this;
		}

		public Builder version(int version) {
			expense.version = version;
			return this;
		}

		public Builder id(Long id) {
			expense.id = id;
			return this;
		}

		public Expense build() {
			return expense;
		}

		private Builder() {
		}
	}
}
