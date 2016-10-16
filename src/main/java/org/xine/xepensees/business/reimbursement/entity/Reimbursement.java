package org.xine.xepensees.business.reimbursement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.DecimalMax;
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

	@NotNull
	@Enumerated
	private Currency currency = Currency.EURO;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_reimbursement_user"))
	private User user;

	@NotNull
	// @Size(min = 1)
	@OneToMany(mappedBy = "reimbursement", cascade = { CascadeType.ALL })
	// @JoinColumn(name = "reimbursement_id", foreignKey = @ForeignKey(name =
	// "fk_expenses_reimbursement_id"))
	private Set<Expense> expenses = new HashSet<>();

	@NotNull
	@DecimalMax(value = "9999")
	private BigDecimal total = BigDecimal.ZERO;

	protected Reimbursement() {}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected int getVersion() {
		return version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	public LocalDate getDate() {
		return date;
	}

	protected void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	protected void setUser(User user) {
		this.user = user;
	}

	public Set<Expense> getExpenses() {
		return Collections.unmodifiableSet(expenses);
	}

	protected void setExpenses(Set<Expense> expenses) {
		this.expenses = expenses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}
	

	public void add(Expense... expensess) {
		if (expenses == null) {
			throw new IllegalArgumentException("the expenses can't be null");
		}
		
		final List<Expense> expensesOfOthersUsers = Arrays.stream(expensess).
				filter(e -> !e.getUser().equals(user)).
				collect(Collectors.toList());
		
		if (!expensesOfOthersUsers.isEmpty()) {
			throw new IllegalArgumentException("all the expenses must be of the same user.");
		}
		
		Arrays.stream(expensess).forEach(e -> {
			expenses.add(e);
			e.setReimbursement(this);
			total = total.add(e.getAmount());
		});

	}


	public void remove(Expense... expensess) {
		if (expenses == null) {
			throw new IllegalArgumentException("the expenses can't be null");
		}

		Arrays.stream(expensess).forEach(e -> {
			e.setReimbursement(null);
		});

		expenses.removeAll(Arrays.asList(expenses));
	}
	
	public void clear() {
		user = null;

		remove(expenses.toArray(new Expense[0]));

		total = BigDecimal.ZERO;
	}

	public BigDecimal getTotal() {
		return total;
	}

	protected void setTotal(BigDecimal total) {
		this.total = total;
	}

	public static Builder builder(User user) {
		return new Builder(user);
	}
	
	public static class Builder {
		private final Reimbursement reimbursement;
		
		public Builder(User user) {
			reimbursement = new Reimbursement();
			reimbursement.user = user;
		}

		public Reimbursement build() {
			return reimbursement;
		}

		public Builder id(Long id) {
			reimbursement.id = id;
			return this;
		}

		public Builder version(int version) {
			reimbursement.version = version;
			return this;
		}

		public Builder date(LocalDate date) {
			reimbursement.date = date;
			return this;
		}

		public Builder currency(Currency currency) {
			reimbursement.currency = currency;
			return this;
		}

		public Builder user(User user) {
			reimbursement.user = user;
			return this;
		}

		public Builder addExpense(Expense e) {
			reimbursement.add(e);
			return this;
		}

	}

	public static Reimbursement empty() {
		return new Reimbursement();
	}



}
