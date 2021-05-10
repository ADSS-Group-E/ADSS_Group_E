BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Supplier" (
	"companyNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"paymentMethod"	TEXT NOT NULL,
	"bankAccount"	TEXT NOT NULL,
	PRIMARY KEY("companyNumber")
);
CREATE TABLE IF NOT EXISTS "Contact" (
	"contactName"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	PRIMARY KEY("contactName","email","companyNumber")
);
CREATE TABLE IF NOT EXISTS "QuantityWriter" (
	"ID"	INTEGER NOT NULL,
	"minPriceDiscount"	INTEGER,
	"regularCostumerDiscount"	INTEGER,
	"companyNumber"	INTEGER,
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "InventoryItem" (
	"name"	TEXT NOT NULL,
	"manufacturer"	TEXT NOT NULL,
	"location"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	FOREIGN KEY("name") REFERENCES "Item"("name"),
	PRIMARY KEY("name","location")
);
CREATE TABLE IF NOT EXISTS "SpecificProduct" (
	"ID"	INTEGER NOT NULL,
	"ExpirationDate"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "Category" (
	"ID"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"superID"	INTEGER,
	FOREIGN KEY("superID") REFERENCES "Category"("ID"),
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "Discount" (
	"ID"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"discPrecentage"	REAL NOT NULL,
	"startDate"	TEXT NOT NULL,
	"endDate"	TEXT NOT NULL,
	"type"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "Report" (
	"ID"	INTEGER NOT NULL,
	"creationDate"	TEXT NOT NULL,
	"reportTag"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "StepDiscount" (
	"ID"	INTEGER NOT NULL,
	"stepPrice"	INTEGER NOT NULL,
	"precentage"	INTEGER NOT NULL,
	"QWID"	INTEGER NOT NULL,
	FOREIGN KEY("QWID") REFERENCES "QuantityWriter"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Item" (
	"name"	TEXT NOT NULL,
	"ID"	INTEGER NOT NULL,
	PRIMARY KEY("ID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Order" (
	"orderID"	INTEGER NOT NULL,
	"date"	TEXT NOT NULL,
	"periodicDelivery"	BOOLEAN NOT NULL CHECK("periodicDelivery" IN (0, 1)),
	"needsDelivery"	BOOLEAN NOT NULL CHECK("needsDelivery" IN (0, 1)),
	PRIMARY KEY("orderID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "OrderItems" (
	"productID"	INTEGER NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	"orderID"	INTEGER NOT NULL,
	"price"	INTEGER NOT NULL CHECK("price" >= 0),
	"quantity"	INTEGER NOT NULL CHECK("quantity" >= 0),
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	FOREIGN KEY("orderID") REFERENCES "Order"("orderID"),
	FOREIGN KEY("productID") REFERENCES "Product"("ID"),
	PRIMARY KEY("productID","companyNumber","orderID")
);
CREATE TABLE IF NOT EXISTS "SupplierItem" (
	"productID"	INTEGER NOT NULL,
	"companyNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"quantity"	TEXT NOT NULL CHECK("quantity" >= 0),
	"price"	INTEGER NOT NULL,
	"supplierCN"	INTEGER,
	FOREIGN KEY("productID") REFERENCES "Product"("ID"),
	FOREIGN KEY("companyNumber") REFERENCES "Supplier"("companyNumber"),
	PRIMARY KEY("productID","companyNumber")
);
CREATE TABLE IF NOT EXISTS "Product" (
	"ID"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"storeLocation"	TEXT NOT NULL,
	"storageLocation"	TEXT NOT NULL,
	"manufacturer"	TEXT NOT NULL,
	"buyPrice"	REAL NOT NULL,
	"sellPrice"	REAL NOT NULL,
	"minAmount"	INTEGER NOT NULL,
	"categoryID"	INTEGER NOT NULL,
	"buyDiscountID"	INTEGER,
	"sellDiscountID"	INTEGER,
	FOREIGN KEY("buyDiscountID") REFERENCES "Discount"("ID"),
	FOREIGN KEY("sellDiscountID") REFERENCES "Discount"("ID"),
	PRIMARY KEY("ID")
);
COMMIT;
