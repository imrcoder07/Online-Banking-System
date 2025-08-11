import java.util.*;

class Account {
    String accountNumber;
    String name;
    double balance;
    String pin;
    List<String> transactions = new ArrayList<>();

    public Account(String accountNumber, String name, String pin) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.pin = pin;
        this.balance = 0.0;
    }

    public boolean checkPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited: " + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew: " + amount);
            return true;
        }
        return false;
    }

    public void transfer(Account toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            transactions.add("Transferred " + amount + " to " + toAccount.accountNumber);
            toAccount.transactions.add("Received " + amount + " from " + accountNumber);
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }

    public void printTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String t : transactions) {
                System.out.println(t);
            }
        }
    }
}

public class OnlineBanking {
    static Scanner sc = new Scanner(System.in);
    static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== Online Banking System ====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Thank you for using Online Banking. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void createAccount() {
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        if (accounts.containsKey(accNo)) {
            System.out.println("Account already exists!");
            return;
        }
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Set a PIN: ");
        String pin = sc.nextLine();
        accounts.put(accNo, new Account(accNo, name, pin));
        System.out.println("Account created successfully!");
    }

    static void login() {
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        Account acc = accounts.get(accNo);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();
        if (!acc.checkPin(pin)) {
            System.out.println("Invalid PIN.");
            return;
        }
        System.out.println("Welcome, " + acc.name + "!");
        accountMenu(acc);
    }

    static void accountMenu(Account acc) {
        while (true) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Balance");
            System.out.println("5. Transactions");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter deposit amount: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    acc.deposit(amt);
                    System.out.println("Deposit successful.");
                }
                case 2 -> {
                    System.out.print("Enter withdraw amount: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    if (acc.withdraw(amt))
                        System.out.println("Withdrawal successful.");
                    else
                        System.out.println("Insufficient balance.");
                }
                case 3 -> {
                    System.out.print("Enter recipient account number: ");
                    String toAccNo = sc.nextLine();
                    Account toAcc = accounts.get(toAccNo);
                    if (toAcc == null) {
                        System.out.println("Recipient account not found.");
                        break;
                    }
                    System.out.print("Enter amount to transfer: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    acc.transfer(toAcc, amt);
                }
                case 4 -> System.out.println("Current Balance: " + acc.balance);
                case 5 -> acc.printTransactions();
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
