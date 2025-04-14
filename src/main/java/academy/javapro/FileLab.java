package academy.javapro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileLab {
    private static final List<Map<String, String>> salesData;
    private static final Scanner scanner = new Scanner(System.in);

    static {
        salesData = new ArrayList<>();
        addSalesRecord("1001", "2023-01-15", "John Smith", "P123", "5", "29.99", "149.95", "North");
        addSalesRecord("1002", "2023-01-17", "Sarah Johnson", "P145", "2", "49.99", "99.98", "South");
        addSalesRecord("1003", "2023-01-18", "Michael Brown", "P123", "3", "29.99", "89.97", "East");
        addSalesRecord("1004", "2023-01-20", "Emma Wilson", "P187", "1", "199.99", "199.99", "West");
        addSalesRecord("1005", "2023-01-22", "Robert Davis", "P145", "4", "49.99", "199.96", "North");
        addSalesRecord("1006", "2023-01-25", "Jennifer Lee", "P187", "2", "199.99", "399.98", "South");
        addSalesRecord("1007", "2023-01-26", "David Miller", "P123", "10", "29.99", "299.90", "East");
        addSalesRecord("1008", "2023-01-27", "Amanda Clark", "P254", "3", "19.99", "59.97", "West");
        addSalesRecord("1009", "2023-01-28", "Thomas Johnson", "P187", "1", "199.99", "199.99", "North");
        addSalesRecord("1010", "2023-01-30", "Lisa Taylor", "P254", "8", "19.99", "159.92", "South");
        addSalesRecord("1011", "2023-02-02", "James Wilson", "P123", "6", "29.99", "179.94", "East");
        addSalesRecord("1012", "2023-02-03", "Patricia Brown", "P145", "3", "49.99", "149.97", "West");
        addSalesRecord("1013", "2023-02-05", "Richard Moore", "P254", "5", "19.99", "99.95", "North");
        addSalesRecord("1014", "2023-02-07", "Elizabeth Davis", "P187", "1", "199.99", "199.99", "South");
        addSalesRecord("1015", "2023-02-10", "Charles Garcia", "P123", "4", "29.99", "119.96", "East");
    }

    private static void addSalesRecord(String orderId, String date, String customerName,
                                       String productId, String quantity, String unitPrice,
                                       String totalAmount, String region) {
        Map<String, String> record = new HashMap<>();
        record.put("OrderID", orderId);
        record.put("Date", date);
        record.put("CustomerName", customerName);
        record.put("ProductID", productId);
        record.put("Quantity", quantity);
        record.put("UnitPrice", unitPrice);
        record.put("TotalAmount", totalAmount);
        record.put("Region", region);
        salesData.add(record);
    }

    public static void main(String[] args) {
        System.out.println("===== Simplified File Data Processor =====\n");
        System.out.println("Original Data:");
        displayData(salesData);

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    System.out.println("\n===== Filter: Quantity > 5 (Large Orders) =====");
                    displayData(filterQuantityGreaterThan(salesData, 5));
                    break;
                case 2:
                    System.out.println("\n===== Filter: UnitPrice > 100 (Expensive Products) =====");
                    displayData(filterUnitPriceGreaterThan(salesData, 100));
                    break;
                case 3:
                    System.out.println("\n===== Filter: Region = \"North\" =====");
                    displayData(filterByRegion(salesData, "North"));
                    break;
                case 4:
                    running = false;
                    System.out.println("Thank you for using the File Data Processor!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n==== Filter Operations ====");
        System.out.println("1. Find large orders (Quantity > 5)");
        System.out.println("2. Find expensive products (UnitPrice > 100)");
        System.out.println("3. Find orders from North region");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    private static int getMenuChoice() {
        int choice = 0;
        boolean valid = false;
        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                valid = (choice >= 1 && choice <= 4);
                if (!valid) {
                    System.out.print("Please enter a number between 1 and 4: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        } while (!valid);
        return choice;
    }

    private static void displayData(List<Map<String, String>> data) {
        System.out.println(String.format("%-8s %-12s %-20s %-10s %-8s %-10s %-12s %-8s",
                "OrderID", "Date", "CustomerName", "ProductID", "Quantity",
                "UnitPrice", "TotalAmount", "Region"));
        System.out.println("-".repeat(90));
        for (Map<String, String> record : data) {
            System.out.println(String.format("%-8s %-12s %-20s %-10s %-8s %-10s %-12s %-8s",
                    record.get("OrderID"),
                    record.get("Date"),
                    record.get("CustomerName"),
                    record.get("ProductID"),
                    record.get("Quantity"),
                    record.get("UnitPrice"),
                    record.get("TotalAmount"),
                    record.get("Region")));
        }
        System.out.println("-".repeat(90));
        System.out.println("Total records: " + data.size());
    }

    private static List<Map<String, String>> filterQuantityGreaterThan(List<Map<String, String>> data, int minQuantity) {
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> record : data) {
            try {
                if (Integer.parseInt(record.get("Quantity")) > minQuantity) {
                    result.add(record);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity in order: " + record.get("OrderID"));
            }
        }
        return result;
    }

    private static List<Map<String, String>> filterUnitPriceGreaterThan(List<Map<String, String>> data, double minPrice) {
        List<Map<String, String>> result = new ArrayList<>();
        int i = 0;
        while (i < data.size()) {
            Map<String, String> record = data.get(i);
            try {
                if (Double.parseDouble(record.get("UnitPrice")) > minPrice) {
                    result.add(record);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid unit price in order: " + record.get("OrderID"));
            }
            i++;
        }
        return result;
    }

    private static List<Map<String, String>> filterByRegion(List<Map<String, String>> data, String targetRegion) {
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> record : data) {
            String region = record.get("Region");
            if (region == null) continue;
            // Using a simple switch with if condition for matching the target region.
            switch (region.toLowerCase()) {
                case "north":
                case "south":
                case "east":
                case "west":
                    if (region.equalsIgnoreCase(targetRegion)) {
                        result.add(record);
                    }
                    break;
                default:
                    System.out.println("Unknown region: " + region);
                    break;
            }
        }
        return result;
    }
}
