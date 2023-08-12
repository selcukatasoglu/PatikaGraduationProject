import java.util.Date;
import java.util.ArrayList;
import java.util.List;

enum BillType {
    TELEFON,
    INTERNET,
    SU
}

class MemberAccount {
    private String id;
    private String code;
    private String firstName;
    private String lastName;
    private double balance;

    public MemberAccount(String id, String code, String firstName, String lastName, double balance) {
        this.id = id;
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }
}

class Fatura {
    private double amount;
    private Date processDate;
    private BillType billType;

    public Fatura(double amount, Date processDate, BillType billType) {
        this.amount = amount;
        this.processDate = processDate;
        this.billType = billType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public BillType getBillType() {
        return billType;
    }
}

class MemberAccountService {
    private List<MemberAccount> memberAccounts;

    public MemberAccountService() {
        memberAccounts = new ArrayList<>();
    }

    public void createMemberAccount(MemberAccount member) {
        memberAccounts.add(member);
        System.out.println("Üye hesabı oluşturuldu: " + member.getId());
    }

    public MemberAccount getMemberAccount(String memberId) {
        for (MemberAccount member : memberAccounts) {
            if (member.getId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    public void updateMemberAccount(MemberAccount member) {
        for (int i = 0; i < memberAccounts.size(); i++) {
            if (memberAccounts.get(i).getId().equals(member.getId())) {
                memberAccounts.set(i, member);
                System.out.println("Üye hesabı güncellendi: " + member.getId());
                break;
            }
        }
    }

    public void deleteMemberAccount(String memberId) {
        for (int i = 0; i < memberAccounts.size(); i++) {
            if (memberAccounts.get(i).getId().equals(memberId)) {
                memberAccounts.remove(i);
                System.out.println("Üye hesabı silindi: " + memberId);
                break;
            }
        }
    }
}

class FaturaService {
    public void makePayment(MemberAccount member, Fatura fatura) throws InsufficientBalanceException {
        if (member.getBalance() >= fatura.getAmount()) {
            member.setBalance(member.getBalance() - fatura.getAmount());
            System.out.println("Ödeme başarıyla gerçekleştirildi.");
        } else {
            throw new InsufficientBalanceException("Yetersiz bakiye.");
        }
    }

    public void queryBill(MemberAccount member, BillType billType) {
        System.out.println("Fatura sorgulama işlemi gerçekleştirildi.");
    }

    public void cancelPayment(MemberAccount member, Fatura fatura) {
        member.setBalance(member.getBalance() + fatura.getAmount());
        System.out.println("Ödeme iptali işlemi gerçekleştirildi.");
    }
}

class Client {
    public void payBill(MemberAccount member, BillType billType, double amount, Date processDate) throws InsufficientBalanceException {
        FaturaService faturaService = new FaturaService();
        Fatura fatura = new Fatura(amount, processDate, billType);
        faturaService.makePayment(member, fatura);
    }

    public void queryBill(MemberAccount member, BillType billType) {
        FaturaService faturaService = new FaturaService();
        faturaService.queryBill(member, billType);
    }

    public void cancelPayment(MemberAccount member, Fatura fatura) {
        FaturaService faturaService = new FaturaService();
        faturaService.cancelPayment(member, fatura);
    }
}

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        MemberAccountService memberAccountService = new MemberAccountService();

        MemberAccount member1 = new MemberAccount("123", "AB", "John", "Doe", 500.0);
        MemberAccount member2 = new MemberAccount("456", "CD", "Alice", "Smith", 800.0);

        memberAccountService.createMemberAccount(member1);
        memberAccountService.createMemberAccount(member2);

        MemberAccount foundMember = memberAccountService.getMemberAccount("123");
        if (foundMember != null) {
            System.out.println("Bulunan Üye Adı: " + foundMember.getFirstName());
        } else {
            System.out.println("Üye bulunamadı.");
        }

        foundMember.setBalance(600.0);
        memberAccountService.updateMemberAccount(foundMember);

        memberAccountService.deleteMemberAccount("456");
    }
}
