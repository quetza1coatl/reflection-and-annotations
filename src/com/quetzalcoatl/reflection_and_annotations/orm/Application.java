package com.quetzalcoatl.reflection_and_annotations.orm;

public class Application {
    public static void main(String[] args) throws Exception {
        TransactionHistory jackHistory = new TransactionHistory(991377, "Jack", "Credit", 231);
        TransactionHistory samanthaHistory = new TransactionHistory(994873, "Samantha", "Debit", 44);
        TransactionHistory wotanHistory = new TransactionHistory(177354, "Wotan", "Credit", 236);

        Hibernate<TransactionHistory> hibernate = Hibernate.getConnection();
//        hibernate.write(jackHistory);
//        hibernate.write(samanthaHistory);
//        hibernate.write(wotanHistory);

        TransactionHistory result = hibernate.read(TransactionHistory.class, 2L);
        System.out.println(result);
    }
}
