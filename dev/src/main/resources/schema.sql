BEGIN TRANSACTION;
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
	PRIMARY KEY("ID"),
	FOREIGN KEY("sellDiscountID") REFERENCES "Discount"("ID")
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
DROP TABLE IF EXISTS "ItemGroup";
CREATE TABLE IF NOT EXISTS "ItemGroup" (
	"ID"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"PID"	INTEGER NOT NULL,
	"location"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	"priceBoughtAt"	REAL NOT NULL,
	"expiration"	TEXT NOT NULL,
	FOREIGN KEY("PID") REFERENCES "Product"("ID")
);
DROP TABLE IF EXISTS "SOrder";
CREATE TABLE IF NOT EXISTS "SOrder" (
	"orderID"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"date"	TEXT NOT NULL,
	"periodicDelivery"	BOOLEAN NOT NULL CHECK("periodicDelivery" IN (0,1)),
	"needsDelivery"	BOOLEAN NOT NULL CHECK("needsDelivery" IN (0,1))
);
DROP TABLE IF EXISTS "SupplierItem";
CREATE TABLE IF NOT EXISTS "SupplierItem" (
	"productID"	INTEGER NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"quantity"	TEXT NOT NULL CHECK("quantity">=0),
	"price"	INTEGER NOT NULL,
	"supplierCN"	INTEGER,
	PRIMARY KEY("productID","companyNumber"),
	FOREIGN KEY("productID") REFERENCES "Product"("ID"),
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber")
);
DROP TABLE IF EXISTS "OrderItems";
CREATE TABLE IF NOT EXISTS "OrderItems" (
	"productID"	INTEGER NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	"orderID"	INTEGER NOT NULL,
	"price"	INTEGER NOT NULL CHECK("price">=0),
	"quantity"	INTEGER NOT NULL CHECK("quantity">=0),
	PRIMARY KEY("productID","companyNumber","orderID"),
	FOREIGN KEY("productID") REFERENCES "Product"("ID"),
	FOREIGN KEY("orderID") REFERENCES "SOrder"("orderID"),
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber")
);
DROP TABLE IF EXISTS "StepDiscount";
CREATE TABLE IF NOT EXISTS "StepDiscount" (
	"ID"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"stepPrice"	INTEGER NOT NULL,
	"precentage"	INTEGER NOT NULL,
	"QWID"	INTEGER NOT NULL,
	FOREIGN KEY("QWID") REFERENCES "QuantityWriter"("ID")
);
DROP TABLE IF EXISTS "Report";
CREATE TABLE IF NOT EXISTS "Report" (
	"ID"	INTEGER NOT NULL,
	"creationDate"	TEXT NOT NULL,
	"reportTag"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "Category";
CREATE TABLE IF NOT EXISTS "Category" (
	"ID"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"superID"	INTEGER,
	PRIMARY KEY("ID"),
	FOREIGN KEY("superID") REFERENCES "Category"("ID")
);
DROP TABLE IF EXISTS "SpecificProduct";
CREATE TABLE IF NOT EXISTS "SpecificProduct" (
	"ID"	INTEGER NOT NULL,
	"ExpirationDate"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
DROP TABLE IF EXISTS "InventoryItem";
CREATE TABLE IF NOT EXISTS "InventoryItem" (
	"name"	TEXT NOT NULL,
	"manufacturer"	TEXT NOT NULL,
	"location"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	PRIMARY KEY("name","location"),
	FOREIGN KEY("name") REFERENCES "ItemGroup"("name")
);
DROP TABLE IF EXISTS "QuantityWriter";
CREATE TABLE IF NOT EXISTS "QuantityWriter" (
	"ID"	INTEGER NOT NULL,
	"minPriceDiscount"	INTEGER,
	"regularCostumerDiscount"	INTEGER,
	"companyNumber"	INTEGER,
	PRIMARY KEY("ID"),
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber")
);
DROP TABLE IF EXISTS "Contact";
CREATE TABLE IF NOT EXISTS "Contact" (
	"contactName"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	PRIMARY KEY("contactName","email","companyNumber"),
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber")
);
DROP TABLE IF EXISTS "Supplier";
CREATE TABLE IF NOT EXISTS "Supplier" (
	"companyNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"paymentMethod"	TEXT NOT NULL,
	"bankAccount"	TEXT NOT NULL,
	PRIMARY KEY("companyNumber")
);
DROP TABLE IF EXISTS "SupplyDays";
CREATE TABLE IF NOT EXISTS "SupplyDays" (
	"companyNumber"	INTEGER NOT NULL,
	"weekDay"	TEXT NOT NULL,
	PRIMARY KEY("companyNumber","weekDay")
);
COMMIT;
