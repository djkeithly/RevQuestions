//show dbs;

use("gadgets");

const gadgets = db.getSiblingDB("gadget");

gadgets.products.drop();

// Create a collection named products with a JSON Schema validator
gadgets.createCollection("products", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["name", "price", "inStock"],
            properties: {
                name: {
                    bsonType: "string",
                    description: "Must be a string and is required.",
                },
                price: {
                    bsonType: ["double", "int"],
                    minimum: 0,
                    description:
                        "Must be a non negative double and is required",
                },
                inStock: {
                    bsonType: "bool",
                    description: "Must be a boolean and is required",
                },
            },
        },
    },
    validationAction: "error",
});

// Use insertMany() to add at least three different gadgets (e.g., Wireless Mouse, Mechanical Keyboard, Gaming Monitor).
// Make sure they match your schema, and include a nested specs sub-document with a brand field (e.g., specs: { brand: "Logitech" }).
// Test your validation by trying to insertOne() a product that violates your rules (like missing the price field or giving it the wrong data type).
gadgets.products.insertMany([
    {
        name: "Wireless Mouse",
        price: 20.0,
        inStock: true,
        specs: { brand: "Logitech" },
    },
    {
        name: "Mechanical Keyboard",
        price: 30.75,
        inStock: true,
        specs: { brand: "Intel" },
    },
    { name: "Gaming Monitor", price: 50.0, inStock: false },
]);

// This fails
// gadgets.products.insertOne({
//     name: "WIRELESS Error",
//     price: -20,
//     inStock: true,
// });

// Choose one product and use updateOne() with $set to add a new top-level field called category with the value "Accessories".
// Use $inc to increase its price by 15 dollars.
// Add an array field called tags to that product using $push to add "wireless". Then, use $push again to add "bestseller".

gadgets.products.updateOne(
    { name: "Wireless Mouse" },
    { $set: { category: "Accessories", tags: [] }, $inc: { price: 15 } },
);

gadgets.products.updateOne(
    { name: "Wireless Mouse" },
    { $push: { tags: "Wireless" } },
);

gadgets.products.updateOne(
    { name: "Wireless Mouse" },
    { $push: { tags: "Bestseller" } },
);

// Decide you don't want "wireless" after all, and use $pull to remove it from the tags array.

gadgets.products.updateOne(
    { name: "Wireless Mouse" },
    { $pull: { tags: "Wireless" } },
);

// Find all products priced greater than or equal to a certain amount using $gte.
gadgets.products.find({ price: { $gte: 34 } });

// Find all products made by a specific brand using dot notation (e.g., "specs.brand").
gadgets.products.find({ "specs.brand": "Logitech" });

// Find products whose category matches one in a list using $in.

gadgets.products.find({
    name: { $in: ["Wireless Mouse", "Gaming Monitor", "Other Tech"] },
});

// Create a second collection named orders

gadgets.getSiblingDB("orders");

// Insert a document into orders that links a product's _id to an order (e.g., { productId: <ObjectId_from_product>, quantity: 2 }).

gadgets.orders.insertOne({
    productId: gadgets.products.findOne({ name: "Wireless Mouse" })._id,
    quantity: 2,
});

// Write an aggregation pipeline on the orders collection using $lookup and $unwind to join orders with products.
/**
    Use $project to output a clean customer receipt showing:
        The product name ($product.name)
        The ordered quantity (quantity)
        Hiding the _id field.
 */

gadgets.orders.aggregate([
    {
        $lookup: {
            from: "products",
            localField: "productId",
            foreignField: "_id",
            as: "product",
        },
    },
    {
        $unwind: "$product",
    },
    {
        $project: {
            _id: 0,
            name: "$product.name",
            quantity: 1,
        },
    },
]);
