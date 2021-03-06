BEGIN TRANSACTION;
DROP TABLE IF EXISTS "SupplyDays";
CREATE TABLE IF NOT EXISTS "SupplyDays" (
	"companyNumber"	INTEGER NOT NULL,
	"weekDay"	TEXT NOT NULL,
	PRIMARY KEY("companyNumber","weekDay")
);
DROP TABLE IF EXISTS "Supplier";
CREATE TABLE IF NOT EXISTS "Supplier" (
	"companyNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"paymentMethod"	TEXT NOT NULL,
	"bankAccount"	TEXT NOT NULL,
	PRIMARY KEY("companyNumber")
);
DROP TABLE IF EXISTS "Contact";
CREATE TABLE IF NOT EXISTS "Contact" (
	"contactName"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	PRIMARY KEY("contactName","email","companyNumber")
);
DROP TABLE IF EXISTS "QuantityWriter";
CREATE TABLE IF NOT EXISTS "QuantityWriter" (
	"ID"	INTEGER NOT NULL,
	"minPriceDiscount"	INTEGER,
	"regularCostumerDiscount"	INTEGER,
	"companyNumber"	INTEGER,
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "InventoryItem";
CREATE TABLE IF NOT EXISTS "InventoryItem" (
	"name"	TEXT NOT NULL,
	"manufacturer"	TEXT NOT NULL,
	"location"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	FOREIGN KEY("name") REFERENCES "ItemGroup"("name"),
	PRIMARY KEY("name","location")
);
DROP TABLE IF EXISTS "SpecificProduct";
CREATE TABLE IF NOT EXISTS "SpecificProduct" (
	"ID"	INTEGER NOT NULL,
	"ExpirationDate"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Category";
CREATE TABLE IF NOT EXISTS "Category" (
	"ID"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"superID"	INTEGER,
	FOREIGN KEY("superID") REFERENCES "Category"("ID"),
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Report";
CREATE TABLE IF NOT EXISTS "Report" (
	"ID"	INTEGER NOT NULL,
	"creationDate"	TEXT NOT NULL,
	"reportTag"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "StepDiscount";
CREATE TABLE IF NOT EXISTS "StepDiscount" (
	"ID"	INTEGER NOT NULL,
	"stepPrice"	INTEGER NOT NULL,
	"precentage"	INTEGER NOT NULL,
	"QWID"	INTEGER NOT NULL,
	FOREIGN KEY("QWID") REFERENCES "QuantityWriter"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "OrderItems";
CREATE TABLE IF NOT EXISTS "OrderItems" (
	"productID"	INTEGER NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	"orderID"	INTEGER NOT NULL,
	"price"	INTEGER NOT NULL CHECK("price" >= 0),
	"quantity"	INTEGER NOT NULL CHECK("quantity" >= 0),
	FOREIGN KEY("orderID") REFERENCES "SOrder"("orderID"),
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	FOREIGN KEY("productID") REFERENCES "Product"("ID"),
	PRIMARY KEY("productID","companyNumber","orderID")
);
DROP TABLE IF EXISTS "SupplierItem";
CREATE TABLE IF NOT EXISTS "SupplierItem" (
	"productID"	INTEGER NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"quantity"	TEXT NOT NULL CHECK("quantity" >= 0),
	"price"	INTEGER NOT NULL,
	"supplierCN"	INTEGER,
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	FOREIGN KEY("productID") REFERENCES "Product"("ID"),
	PRIMARY KEY("productID","companyNumber")
);
DROP TABLE IF EXISTS "ItemGroup";
CREATE TABLE IF NOT EXISTS "ItemGroup" (
	"ID"	INTEGER NOT NULL,
	"PID"	INTEGER NOT NULL,
	"location"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	"priceBoughtAt"	REAL NOT NULL,
	"expiration"	TEXT NOT NULL,
	FOREIGN KEY("PID") REFERENCES "Product"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "Discount";
CREATE TABLE IF NOT EXISTS "Discount" (
	"ID"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"discPercentage"	REAL NOT NULL,
	"startDate"	TEXT NOT NULL,
	"endDate"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Product";
CREATE TABLE IF NOT EXISTS "Product" (
	"ID"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"storeLocation"	TEXT NOT NULL,
	"storageLocation"	TEXT NOT NULL,
	"manufacturer"	TEXT NOT NULL,
	"sellPrice"	REAL NOT NULL,
	"minAmount"	INTEGER NOT NULL,
	"categoryID"	INTEGER NOT NULL,
	"sellDiscountID"	INTEGER,
	FOREIGN KEY("sellDiscountID") REFERENCES "Discount"("ID"),
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Workers";
CREATE TABLE IF NOT EXISTS "Workers" (
	"ID"	TEXT NOT NULL,
	"BranchID"	INTEGER NOT NULL,
	"First_Name"	TEXT NOT NULL,
	"Last_Name"	TEXT NOT NULL,
	"Start_Working_Day"	DATE NOT NULL,
	"isWorking"	INTEGER NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Branches";
CREATE TABLE IF NOT EXISTS "Branches" (
	"branchID"	INTEGER NOT NULL,
	"branchManagerID"	TEXT NOT NULL,
	"HRD_ID"	TEXT NOT NULL,
	"logisticsManagerID"	TEXT NOT NULL,
	FOREIGN KEY("branchManagerID") REFERENCES "Workers"("id"),
	FOREIGN KEY("logisticsManagerID") REFERENCES "Workers"("id"),
	FOREIGN KEY("HRD_ID") REFERENCES "Workers"("id"),
	PRIMARY KEY("branchID")
);
DROP TABLE IF EXISTS "BankAccounts";
CREATE TABLE IF NOT EXISTS "BankAccounts" (
	"ID"	TEXT NOT NULL,
	"Bank_Name"	TEXT NOT NULL,
	"Branch"	TEXT NOT NULL,
	"BankAccountID"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Workers"("ID") ON DELETE CASCADE,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "AvailableWorkingDays";
CREATE TABLE IF NOT EXISTS "AvailableWorkingDays" (
	"ID"	TEXT NOT NULL,
	"Day"	TEXT NOT NULL,
	"Shift_Type"	TEXT NOT NULL,
	"IsAvailable"	INTEGER NOT NULL,
	PRIMARY KEY("ID","Day","Shift_Type")
);
DROP TABLE IF EXISTS "Qualifications";
CREATE TABLE IF NOT EXISTS "Qualifications" (
	"ID"	TEXT NOT NULL,
	"Qualification"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Workers"("ID") ON DELETE CASCADE,
	PRIMARY KEY("ID","Qualification")
);
DROP TABLE IF EXISTS "HiringConditions";
CREATE TABLE IF NOT EXISTS "HiringConditions" (
	"ID"	TEXT NOT NULL,
	"salaryPerHour"	REAL NOT NULL,
	"fund"	TEXT NOT NULL,
	"vacationDays"	INTEGER NOT NULL,
	"sickLeavePerMonth"	INTEGER NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Workers"("ID") ON DELETE CASCADE,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "WorkersConstraints";
CREATE TABLE IF NOT EXISTS "WorkersConstraints" (
	"ID"	INT NOT NULL,
	"DayConstraint"	VARCHAR(20) NOT NULL,
	"KindConstraint"	VARCHAR(20) NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Workers"("ID") ON DELETE CASCADE,
	PRIMARY KEY("ID","DayConstraint","KindConstraint")
);
DROP TABLE IF EXISTS "Drivers";
CREATE TABLE IF NOT EXISTS "Drivers" (
	"ID"	TEXT NOT NULL,
	"License_Type"	VARCHAR(10) NOT NULL,
	"Expiration_Date"	DATE NOT NULL,
	"STATUS"	INT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Workers"("ID") ON DELETE CASCADE,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Trucks";
CREATE TABLE IF NOT EXISTS "Trucks" (
	"ID"	VARCHAR(100) NOT NULL,
	"MODEL"	VARCHAR(100) NOT NULL,
	"NETO_WEIGHT"	DOUBLE NOT NULL,
	"MAX_WEIGHT"	DOUBLE NOT NULL,
	"ISUSED"	INT NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Locations";
CREATE TABLE IF NOT EXISTS "Locations" (
	"ID"	INT NOT NULL,
	"NAME"	VARCHAR(100) NOT NULL,
	"ADDRESS"	VARCHAR(100) NOT NULL,
	"TEL_NUMBER"	VARCHAR(100) NOT NULL,
	"CONTACT_NAME"	VARCHAR(100) NOT NULL,
	"SHIIPING_EREA"	VARCHAR(100) NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Orders";
CREATE TABLE IF NOT EXISTS "Orders" (
	"ID"	INT NOT NULL,
	"SUPPLIER"	VARCHAR(100) NOT NULL,
	"TARGET_LOCATION"	INT NOT NULL,
	"TOTAL_WEIGHT"	DOUBLE NOT NULL,
	FOREIGN KEY("TARGET_LOCATION") REFERENCES "Locations"("ID") ON DELETE Restrict,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Deliveries";
CREATE TABLE IF NOT EXISTS "Deliveries" (
	"ID"	VARCHAR(100) NOT NULL,
	"DELIVERY_DATE"	DATE NOT NULL,
	"DELIVER_TIME"	TIME NOT NULL,
	"DRIVER_ID"	TEXT NOT NULL,
	"SOURCE_LOCATION"	INT NOT NULL,
	"WEIGHT"	DOUBLE NOT NULL,
	"TRUCK_ID"	VARCHAR(100) NOT NULL,
	"STATUS"	VARCHAR(100) NOT NULL,
	PRIMARY KEY("ID"),
	FOREIGN KEY("DRIVER_ID") REFERENCES "Drivers"("ID") ON DELETE RESTRICT,
	FOREIGN KEY("TRUCK_ID") REFERENCES "Trucks"("ID") ON DELETE RESTRICT,
	FOREIGN KEY("SOURCE_LOCATION") REFERENCES "Locations"("ID") ON DELETE RESTRICT
);
DROP TABLE IF EXISTS "OrdersForDelivery";
CREATE TABLE IF NOT EXISTS "OrdersForDelivery" (
	"DELIVERY_ID"	VARCHAR(100) NOT NULL,
	"ORDER_ID"	INT NOT NULL,
	PRIMARY KEY("DELIVERY_ID","ORDER_ID"),
	FOREIGN KEY("DELIVERY_ID") REFERENCES "Deliveries"("ID") ON DELETE RESTRICT,
	FOREIGN KEY("ORDER_ID") REFERENCES "Orders"("ID") ON DELETE RESTRICT
);
DROP TABLE IF EXISTS "LocationsForDelivery";
CREATE TABLE IF NOT EXISTS "LocationsForDelivery" (
	"DELIVERY_ID"	VARCHAR(100) NOT NULL,
	"LOCATION_ID"	INT NOT NULL,
	PRIMARY KEY("DELIVERY_ID","LOCATION_ID"),
	FOREIGN KEY("DELIVERY_ID") REFERENCES "Deliveries"("ID") ON DELETE RESTRICT,
	FOREIGN KEY("LOCATION_ID") REFERENCES "Locations"("ID") ON DELETE RESTRICT
);
DROP TABLE IF EXISTS "ShiftDemands";
CREATE TABLE IF NOT EXISTS "ShiftDemands" (
	"Date"	DATE NOT NULL,
	"ShiftType"	TEXT NOT NULL,
	"cashierAmount"	INTEGER NOT NULL,
	"storeKeeperAmount"	INTEGER NOT NULL,
	"arrangerAmount"	INTEGER NOT NULL,
	"guardAmount"	INTEGER NOT NULL,
	"assistantAmount"	INTEGER NOT NULL,
	"deliveryRequired"	INTEGER NOT NULL,
	"BranchID"	INTEGER NOT NULL,
	PRIMARY KEY("Date","ShiftType","BranchID")
);
DROP TABLE IF EXISTS "Shifts";
CREATE TABLE IF NOT EXISTS "Shifts" (
	"Date"	DATE NOT NULL,
	"ShiftType"	TEXT NOT NULL,
	"BranchID"	INTEGER NOT NULL,
	"ShiftManagerID"	TEXT NOT NULL,
	"DriverID"	TEXT,
	PRIMARY KEY("Date","ShiftType","BranchID"),
	FOREIGN KEY("Date","ShiftType","BranchID") REFERENCES "ShiftDemands"("Date","ShiftType","BranchID") ON DELETE CASCADE,
	FOREIGN KEY("DriverID") REFERENCES "Drivers"("ID") ON DELETE CASCADE,
	FOREIGN KEY("ShiftManagerID") REFERENCES "Workers"("ID") ON DELETE CASCADE
);
DROP TABLE IF EXISTS "workersAtShift";
CREATE TABLE IF NOT EXISTS "workersAtShift" (
	"Date"	DATE NOT NULL,
	"ShiftType"	TEXT NOT NULL,
	"BranchID"	INTEGER NOT NULL,
	"workerID"	TEXT NOT NULL,
	"workAs"	TEXT NOT NULL,
	PRIMARY KEY("Date","ShiftType","BranchID","WorkerID","workAs"),
	FOREIGN KEY("workerID","workAs") REFERENCES "Qualifications"("ID","Qualification") ON DELETE CASCADE,
	FOREIGN KEY("Date","ShiftType","BranchID") REFERENCES "Shifts"("Date","ShiftType","BranchID") ON DELETE CASCADE
);
DROP TABLE IF EXISTS "SOrder";
CREATE TABLE IF NOT EXISTS "SOrder" (
	"orderID"	INTEGER NOT NULL,
	"date"	TEXT NOT NULL,
	"periodicDelivery"	BOOLEAN NOT NULL CHECK("periodicDelivery" IN (0, 1)),
	"needsDelivery"	BOOLEAN NOT NULL CHECK("needsDelivery" IN (0, 1)),
	"weight"	INTEGER NOT NULL,
	"isDelivered"	INTEGER NOT NULL CHECK("isDelivered" IN (0, 1)),
	"locationID"	INTEGER NOT NULL,
	PRIMARY KEY("orderID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "ItemsForOrder";
CREATE TABLE IF NOT EXISTS "ItemsForOrder" (
	"ORDER_ID"	INT NOT NULL,
	"ITEM"	INTEGER NOT NULL,
	"QUANTITY"	INT NOT NULL,
	PRIMARY KEY("ORDER_ID","ITEM"),
	FOREIGN KEY("ORDER_ID") REFERENCES "Orders"("ID") ON DELETE RESTRICT,
	FOREIGN KEY("ITEM") REFERENCES "Product"("ID")
);
COMMIT;
