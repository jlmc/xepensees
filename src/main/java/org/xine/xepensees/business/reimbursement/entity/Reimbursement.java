package org.xine.xepensees.business.reimbursement.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.xine.xepensees.business.expense.entity.Currency;
import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.persistence.control.LocalDateConverter;
import org.xine.xepensees.business.user.entity.User;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "t_reimbursement")
public class Reimbursement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@NotNull
	@Convert(converter = LocalDateConverter.class)
	private LocalDate date;

	@Enumerated
	@NotNull
	private Currency currency;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_reimbursement_user"))
	private User user;

	@OneToMany
	@JoinColumn(name = "expense_id", foreignKey = @ForeignKey(name = "fk_expenses_reimbursement_id"))
	private Set<Expense> expenses = new HashSet<>();

	protected Reimbursement() {}

	public Long getId() {
		return this.id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected int getVersion() {
		return this.version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	public LocalDate getDate() {
		return this.date;
	}

	protected void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return this.user;
	}

	protected void setUser(User user) {
		this.user = user;
	}

	public Set<Expense> getExpenses() {
		return Collections.unmodifiableSet(this.expenses);
	}

	protected void setExpenses(Set<Expense> expenses) {
		this.expenses = expenses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Reimbursement other = (Reimbursement) obj;
		return Objects.equals(this.id, other.id);
	}

	public void add(Expense... expenses) {
		if (expenses == null) {
			throw new IllegalArgumentException("the expenses can't be null");
		}

		this.expenses.addAll(Arrays.asList(expenses));
	}

	public void remove(Expense... expenses) {
		if (expenses == null) {
			throw new IllegalArgumentException("the expenses can't be null");
		}

		this.expenses.removeAll(Arrays.asList(expenses));
	}

}
