import java.util.Scanner;

class Orders {

    private String orderId;
    private String customerId;
    private String customerName;
    private int qty;
    private double value;
    private int orderStaus;
    private final int CANCEL = 0;
    private final int PREPARING = 1;
    private final int DELIVERED = 2;

    int getCancel() {
        return CANCEL;
    }

    int getPreparing() {
        return PREPARING;
    }

    int getDelieverd() {
        return DELIVERED;
    }

    Orders(String orderId, String customerId, String customerName, int qty, double value, int orderStaus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        if (qty > 0) {
            this.qty = qty;
        }
        this.orderStaus = orderStaus;
        this.value = value;

    }

    void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    void setQty(int qty) {
        if (qty > 0) {
            this.qty = qty;
        }
    }

    void setOrderStatus(int orderStaus) {
        this.orderStaus = orderStaus;
    }

    void setValue(double value) {
        this.value = value;
    }

    String getOrderId() {
        return orderId;
    }

    int getOrderStatus() {
        return orderStaus;
    }

    String getCustomerId() {
        return customerId;
    }

    String getCustomerName() {
        return customerName;
    }

    int getQty() {
        return qty;
    }

    double getValue() {
        return value;
    }

}

class List {
    private Orders[] dataArray;
    private int nextIndex = 0;
    private double loadfact;

    List(int n, double loadfact) {
        dataArray = new Orders[n];
        this.loadfact = loadfact;

    }

    private void extendArray() {
        int size = dataArray.length + (int) (dataArray.length * loadfact);
        Orders[] tempArr = new Orders[size];
        for (int i = 0; i < dataArray.length; i++) {
            tempArr[i] = dataArray[i];
        }
        dataArray = tempArr;
    }

    public void add(Orders data) {
        if (isFull()) {
            extendArray();
        }
        dataArray[nextIndex++] = data;

    }

    public void remove(int index) {
        if (isExist(index)) {
            for (int i = index; i < nextIndex - 1; i++) {

                dataArray[i] = dataArray[i + 1];

            }
            nextIndex--;
        }
    }

    public boolean isExist(int index) {
        if (index < nextIndex && index >= 0) {
            return true;
        }
        return false;
    }

    public void add(int index, Orders data) {
        if (index >= 0 && index <= nextIndex) {
            for (int i = nextIndex; i > index; i--) {
                dataArray[i] = dataArray[i - 1];
            }
            dataArray[index] = data;
        }
        nextIndex++;

    }

    private boolean isEmpty() {
        return nextIndex <= 0;
    }

    private boolean isFull() {
        return nextIndex >= dataArray.length;
    }

    public void printList() {
        System.out.print("[");
        for (int i = 0; i < nextIndex; i++) {
            System.out.print(dataArray[i] + ",");
        }
        System.out.println(isEmpty() ? "Empty]" : "\b]");
    }

    public void deQueue() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
        } else {
            for (int i = 0; i < dataArray.length - 1; i++) {
                dataArray[i] = dataArray[i + 1];
            }
            nextIndex--;
        }
    }

    public int size() {
        return nextIndex;
    }

    public void clear() {
        nextIndex = 0;
    }

    public boolean contains(Orders data) {
        for (int i = 0; i < nextIndex; i++) {
            if (dataArray[i] == data) {
                return true;
            }
        }
        return false;
    }

    public Orders peek() {
        return dataArray[0];
    }

    public Orders poll() {
        Orders num = dataArray[0];
        deQueue();
        return num;
    }

    public Orders[] toArray() {
        Orders[] arr = new Orders[nextIndex];
        for (int i = 0; i < nextIndex; i++) {
            arr[i] = dataArray[i];
        }
        return arr;
    }

    public Orders get(int index) {
        if (isExist(index)) {
            return dataArray[index];
        }
        return null;
    }
}

class BurgerShop {

    public static List list = new List(2, 0.5);

    public static void placeOrder() {

        Scanner input = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tPLACE ORDER\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n\n");
        System.out.print("ORDER ID - ");
        String orderId = generateOrderId();
        System.out.println(orderId + "\n================\n\n");

        L1: do {
            System.out.print("Enter Customer ID (phone no.): ");
            String customerId = input.next();

            if (customerId.charAt(0) != '0' || customerId.length() != 10) {
                continue L1;
            }
            boolean isExistCustomer = false;
            String customerName = "";
            for (int i = 0; i < list.size(); i++) {
                if (customerId.equals(list.get(i).getCustomerId())) {
                    isExistCustomer = true;
                    System.out.println("Enter Customer Name: " + list.get(i).getCustomerName());
                    customerName = list.get(i).getCustomerName();
                    break;
                }
            }
            if (!isExistCustomer) {
                System.out.print("\nEnter Customer Name: ");
                customerName = input.next();
            }
            System.out.print("Enter Burger Quantity - ");
            int qty = input.nextInt();
            if (qty > 0) {
                double billValue = qty * 500.00;
                System.out.printf("Total value - %.2f", billValue);
                System.out.println();

                L3: do {
                    System.out.print("\tAre you confirm order - ");
                    String option = input.next().toUpperCase();
                    if (option.equalsIgnoreCase("Y")) {
                        Orders newOrder = new Orders(orderId, customerId, customerName, qty, billValue, 1);
                        list.add(newOrder);
                        System.out.println("\n\tYour order is enter to the system successfully...");

                        break L1;
                    } else if (option.equalsIgnoreCase("N")) {
                        System.out.println("\n\tYour order is not enter the system...");
                        clearConsole();
                        return;
                    } else {
                        System.out.println("\tInvalid option..input again...");
                        break L1;
                    }

                } while (true);
            }

        } while (true);

        L4: do {
            System.out.println();
            System.out.print("Do you want to place another order (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                placeOrder();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                home();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L4;
            }
        } while (true);

    }

    public static void searchBestCustomer() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tBEST Customer\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");
        String[] sortCustomerIdArray = new String[0];
        String[] sortCustomerName = new String[0];
        double[] customerTotalBuyingArray = new double[0];

        for (int i = 0; i < list.size(); i++) {
            boolean isExist = false;
            for (int j = 0; j < sortCustomerIdArray.length; j++) {
                if (sortCustomerIdArray[j].equals(list.get(i).getCustomerId())) {
                    customerTotalBuyingArray[j] += list.get(i).getValue();
                    isExist = true;
                }
            }
            if (!isExist) {
                String[] tempSortCustomerArray = new String[sortCustomerIdArray.length + 1];
                String[] tempSortCustomerName = new String[sortCustomerName.length + 1];
                double[] tempCustomerTotalBuyingArray = new double[customerTotalBuyingArray.length + 1];
                for (int j = 0; j < sortCustomerIdArray.length; j++) {
                    tempSortCustomerArray[j] = sortCustomerIdArray[j];
                    tempSortCustomerName[j] = sortCustomerName[j];
                    tempCustomerTotalBuyingArray[j] = customerTotalBuyingArray[j];
                }
                sortCustomerIdArray = tempSortCustomerArray;
                sortCustomerName = tempSortCustomerName;
                customerTotalBuyingArray = tempCustomerTotalBuyingArray;

                sortCustomerIdArray[sortCustomerIdArray.length - 1] = list.get(i).getCustomerId();
                sortCustomerName[sortCustomerName.length - 1] = list.get(i).getCustomerName();
                customerTotalBuyingArray[customerTotalBuyingArray.length - 1] = list.get(i).getValue();
            }
        }
        // sort
        for (int i = 1; i < sortCustomerIdArray.length; i++) {
            for (int j = 0; j < i; j++) {
                if (customerTotalBuyingArray[j] < customerTotalBuyingArray[i]) {
                    String temp = sortCustomerIdArray[j];
                    sortCustomerIdArray[j] = sortCustomerIdArray[i];
                    sortCustomerIdArray[i] = temp;
                    temp = sortCustomerName[j];
                    sortCustomerName[j] = sortCustomerName[i];
                    sortCustomerName[i] = temp;
                    double tempd = customerTotalBuyingArray[j];
                    customerTotalBuyingArray[j] = customerTotalBuyingArray[i];
                    customerTotalBuyingArray[i] = tempd;
                }
            }
        }
        System.out.println("\n----------------------------------------");
        String line1 = String.format("%-14s%-15s%8s", " CustomerID", "Name", "Total");
        System.out.println(line1);
        System.out.println("----------------------------------------");
        for (int i = 0; i < sortCustomerIdArray.length; i++) {

            String line = String.format("%1s%-14s%-15s%8.2f", " ", sortCustomerIdArray[i], sortCustomerName[i],
                    customerTotalBuyingArray[i]);
            System.out.println(line);
            System.out.println("----------------------------------------");

        }

        L: do {
            Scanner input = new Scanner(System.in);
            System.out.print("\n\tDo you want to go back to main menu? (Y/N)> ");
            String exitOption = input.nextLine();
            if (exitOption.equalsIgnoreCase("Y")) {
                clearConsole();
                home();
            } else if (exitOption.equalsIgnoreCase("N")) {
                clearConsole();
                searchBestCustomer();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L;
            }
        } while (true);

    }

    public static void searchOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tSEARCH ORDER DETAILS\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        L1: do {
            System.out.print("Enter order Id - ");
            String orderId = input.next();
            System.out.println();
            for (int i = 0; i < list.size(); i++) {
                if (orderId.equals(list.get(i).getOrderId())) {
                    String status = "";
                    switch (list.get(i).getOrderStatus()) {
                        case 0:
                            status = "Cancel";
                            break;
                        case 1:
                            status = "Preparing";
                            break;
                        case 2:
                            status = "Delivered";
                            break;
                    }
                    System.out.println("---------------------------------------------------------------------------");
                    String line1 = String.format("%-10s%-14s%-12s%-10s%-14s%-10s", " OrderID", " CustomerID", " Name",
                            "Quantity", "  OrderValue", "  OrderStatus");
                    System.out.print(line1);
                    System.out.println(" |");
                    System.out.println("---------------------------------------------------------------------------");
                    String line = String.format("%1s%-10s%-14s%-15s%-10d%-14.2f%-10s", " ", list.get(i).getOrderId(),
                            list.get(i).getCustomerId(), list.get(i).getCustomerName(), list.get(i).getQty(),
                            list.get(i).getValue(), status);
                    System.out.print(line);
                    System.out.println("|");
                    System.out.println("---------------------------------------------------------------------------");
                    break L1;
                }
            }
            L2: do {
                System.out.print("\n\nInvalid Order ID. Do you want to enter again? (Y/N)>");
                String option = input.next();
                if (option.equalsIgnoreCase("Y")) {
                    clearConsole();
                    searchOrder();
                } else if (option.equalsIgnoreCase("N")) {
                    clearConsole();
                    return;
                } else {
                    System.out.println("\tInvalid option..input again...");
                    continue L2;
                }
            } while (true);
        } while (true);
        L3: do {
            System.out.println();
            System.out.print("Do you want to search another order details (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                searchOrder();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                home();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L3;
            }
        } while (true);

    }

    public static void searchCustomer() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tSEARCH CUSTOMER DETAILS\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        L1: do {
            System.out.print("Enter customer Id - ");
            String customerId = input.next();
            System.out.println("\n");
            for (int i = 0; i < list.size(); i++) {
                if (customerId.equals(list.get(i).getCustomerId())) {
                    System.out.println("CustomerID - " + list.get(i).getCustomerId());
                    System.out.println("Name       - " + list.get(i).getCustomerName());
                    System.out.println("\nCustomer Order Details");
                    System.out.println("========================\n");
                    System.out.println("----------------------------------------------");
                    String line = String.format("%-12s%-18s%-14s", " Order_ID", "Order_Quantity", "Total_Value  ");
                    System.out.println(line);
                    System.out.println("----------------------------------------------");
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).getCustomerId().equals(customerId)) {
                            String line2 = String.format("%1s%-12s%-18d%-14.2f", " ", list.get(j).getOrderId(),
                                    list.get(j).getQty(),
                                    list.get(j).getValue());
                            System.out.println(line2);
                        }
                    }
                    System.out.println("----------------------------------------------");
                    break L1;
                }

            }
            L2: do {
                System.out.print("\n\nInvalid Customer ID. Do you want to enter again? (Y/N)>");
                String option = input.next();
                if (option.equalsIgnoreCase("Y")) {
                    clearConsole();
                    searchCustomer();
                } else if (option.equalsIgnoreCase("N")) {
                    clearConsole();
                    return;
                } else {
                    System.out.println("\tInvalid option..input again...");
                    continue L2;
                }
            } while (true);
        } while (true);
        L3: do {
            System.out.println();
            System.out.print("Do you want to search another customer details (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                searchCustomer();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                home();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L3;
            }
        } while (true);
    }

    public static void viewOrders() {
        Scanner input = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tVIEW ORDER LIST\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("[1] Delivered Order");
        System.out.println("[2] Preparing Order");
        System.out.println("[3] Cancel Order");

        System.out.print("\nEnter an option to continue > ");
        int option = input.nextInt();
        switch (option) {
            case 1:
                clearConsole();
                deliverOrder();
                break;
            case 2:
                clearConsole();
                preparingOrder();
                break;
            case 3:
                clearConsole();
                cancelOrder();
                break;
        }
    }

    public static void deliverOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tDELIVERED ORDER\t\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("\n--------------------------------------------------------------");
        String line1 = String.format("%-10s%-15s%-13s%-10s%12s", " OrderID", " CustomerID", " Name", "Quantity",
                "  OrderValue");
        System.out.println(line1);
        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOrderStatus() == 2) {
                String line = String.format("%1s%-10s%-15s%-15s%-10d%8.2f", " ", list.get(i).getOrderId(),
                        list.get(i).getCustomerId(),
                        list.get(i).getCustomerName(), list.get(i).getQty(), list.get(i).getValue());
                System.out.println(line);
                System.out.println("--------------------------------------------------------------");
            }
        }
        L1: do {
            System.out.print("\nDo you want to go to home page (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                home();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                deliverOrder();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L1;
            }
        } while (true);
    }

    public static void preparingOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tPREPARING ORDER\t\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("\n--------------------------------------------------------------");
        String line1 = String.format("%-10s%-15s%-13s%-10s%12s", " OrderID", " CustomerID", " Name", "Quantity",
                "  OrderValue");
        System.out.println(line1);
        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOrderStatus() == 1) {
                String line = String.format("%1s%-10s%-15s%-15s%-10d%8.2f", " ", list.get(i).getOrderId(),
                        list.get(i).getCustomerId(),
                        list.get(i).getCustomerName(), list.get(i).getQty(), list.get(i).getValue());
                System.out.println(line);
                System.out.println("--------------------------------------------------------------");
            }
        }
        L1: do {
            System.out.print("\nDo you want to go to home page (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                home();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                preparingOrder();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L1;
            }
        } while (true);
    }

    public static void cancelOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tCANCEL ORDER\t\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("\n--------------------------------------------------------------");
        String line1 = String.format("%-10s%-15s%-13s%-10s%12s", " OrderID", " CustomerID", " Name", "Quantity",
                "  OrderValue");
        System.out.println(line1);
        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOrderStatus() == 0) {
                String line = String.format("%1s%-10s%-15s%-15s%-10d%8.2f", " ", list.get(i).getOrderId(),
                        list.get(i).getCustomerId(),
                        list.get(i).getCustomerName(), list.get(i).getQty(), list.get(i).getValue());
                System.out.println(line);
                System.out.println("--------------------------------------------------------------");
            }
        }
        L1: do {
            System.out.print("\nDo you want to go to home page (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                home();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                cancelOrder();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L1;
            }
        } while (true);
    }

    public static void updateOrderDetails() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tUPDATE ORDER DETAILS\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        L1: do {
            System.out.print("Enter order Id - ");
            String orderId = input.next();
            System.out.println();
            for (int i = 0; i < list.size(); i++) {
                if (orderId.equals(list.get(i).getOrderId())) {
                    String status = "";
                    switch (list.get(i).getOrderStatus()) {
                        case 0:
                            status = "Cancel";
                            break;
                        case 1:
                            status = "Preparing";
                            break;
                        case 2:
                            status = "Delivered";
                            break;
                    }
                    if (status == "Cancel") {
                        System.out.println("This Order is already cancelled...You can not update this order...");
                    } else if (status == "Delivered") {
                        System.out.println("This Order is already delivered...You can not update this order...");
                    } else {
                        System.out.println("OrderID    - " + list.get(i).getOrderId());
                        System.out.println("CustomerID - " + list.get(i).getCustomerId());
                        System.out.println("Name       - " + list.get(i).getCustomerName());
                        System.out.println("Quantity   - " + list.get(i).getQty());
                        System.out.printf("OrderValue - %.2f", list.get(i).getValue());
                        System.out.println("\nOrderStatus- " + status);

                        System.out.println("\nWhat do you want to update ? ");
                        System.out.println("\t(01) Quantity ");
                        System.out.println("\t(02) Status ");
                        System.out.print("\nEnter your option - ");
                        int option = input.nextInt();
                        Orders order = list.get(i);

                        switch (option) {
                            case 1:
                                clearConsole();
                                System.out.println("\nQuantity Update");
                                System.out.println("================\n");
                                System.out.println("OrderID    - " + list.get(i).getOrderId());
                                System.out.println("CustomerID - " + list.get(i).getCustomerId());
                                System.out.println("Name       - " + list.get(i).getCustomerName());
                                System.out.print("\nEnter your quantity update value - ");
                                int qty = input.nextInt();
                                list.get(i).setQty(qty);
                                ;
                                list.get(i).setValue(qty * 500);
                                System.out.println("\n\tupdate order quantity success fully...");
                                System.out.println("\nnew order quantity - " + list.get(i).getQty());
                                System.out.printf("new order value - %.2f", list.get(i).getValue());
                                break;
                            case 2:
                                clearConsole();
                                System.out.println("\nStatus Update");
                                System.out.println("==============\n");
                                System.out.println("OrderID    - " + list.get(i).getOrderId());
                                System.out.println("CustomerID - " + list.get(i).getCustomerId());
                                System.out.println("Name       - " + list.get(i).getCustomerName());
                                System.out.println("\n\t(0)Cancel");
                                System.out.println("\t(1)Preparing");
                                System.out.println("\t(2)Delivered");
                                System.out.print("\nEnter new order status - ");
                                int s = input.nextInt();
                                list.get(i).setOrderStatus(s);
                                ;
                                switch (list.get(i).getOrderStatus()) {
                                    case 0:
                                        status = "Cancel";
                                        break;
                                    case 1:
                                        status = "Preparing";
                                        break;
                                    case 2:
                                        status = "Delivered";
                                        break;
                                }
                                System.out.println("\n\tUpdate order status successfully...");
                                System.out.println("\nnew order status - " + status);
                                break;
                        }
                    }
                    break L1;
                }
            }
            L3: do {
                System.out.print("\n\nInvalid Order ID. Do you want to enter again? (Y/N)>");
                String option = input.next();
                if (option.equalsIgnoreCase("Y")) {
                    System.out.println("\n");
                    continue L1;
                } else if (option.equalsIgnoreCase("N")) {
                    clearConsole();
                    return;
                } else {
                    System.out.println("\tInvalid option..input again...");
                    continue L3;
                }
            } while (true);
        } while (true);
        L3: do {
            System.out.println();
            System.out.print("Do you want to update another order details (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                updateOrderDetails();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                home();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L3;
            }
        } while (true);
    }

    // generate order Id
    public static String generateOrderId() {

        if (list.size() == 0) {
            return "B0001";
        }
        String lastOrderId = list.get(list.size() - 1).getOrderId();
        int number = Integer.parseInt(lastOrderId.split("B")[1]);
        number++;// 2
        return String.format("B%04d", number);
    }

    public static final void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();

        }
    }

    public static void exit() {
        clearConsole();
        System.out.println("\n\t\tYou left the program...\n");
        System.exit(0);
    }

    public static void home() {

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tiHungry Burger\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("[1] Place Order\t\t\t[2] Search Best Customer");
        System.out.println("[3] Search Order\t\t[4] Search Customer");
        System.out.println("[5] View Orders\t\t\t[6] Update Order Details");

        System.out.println("[7] Exit");

        Scanner input = new Scanner(System.in);
        do {

            System.out.print("\nEnter an option to continue > ");
            char option = input.next().charAt(0);

            switch (option) {
                case '1':
                    clearConsole();
                    placeOrder();
                    break;
                case '2':
                    clearConsole();
                    searchBestCustomer();
                    break;
                case '3':
                    clearConsole();
                    searchOrder();
                    break;
                case '4':
                    clearConsole();
                    searchCustomer();
                    break;
                case '5':
                    clearConsole();
                    viewOrders();
                    break;
                case '6':
                    clearConsole();
                    updateOrderDetails();
                    break;
                case '7':
                    exit();
                    break;
            }
        } while (true);
    }

    public static void main(String[] args) {

        home();

    }
}
