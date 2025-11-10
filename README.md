# Poubelle-intelligente-JAVA

Smart Recycling Center – Manual Test
Project Summary

This project simulates a smart recycling system where users interact with intelligent bins at a recycling center and earn rewards for correctly sorting waste. Users can collect loyalty points that are later exchanged for vouchers from partner companies. The system includes a small element of randomness to reflect that waste recognition is not always perfect.

The purpose of the manual test is to show how the main elements of the system work together: the recycling center, smart bins, users, waste items, and partner companies.

How the Test Works

A recycling center is created.

A smart bin is placed in the center.

A partner company is added to the system.

A user is created and interacts with the smart bin by throwing a piece of waste.

The system calculates loyalty points based on whether the waste is recognized correctly.

The user can then redeem vouchers from partner companies using their loyalty points.

Example Outcomes

Because waste recognition is imperfect, there are two possible scenarios when running the test:

Scenario 1 – Successful Reward

The user throws a piece of glass in the smart bin.

The system recognizes the waste and the user earns 5 loyalty points.

The user sends a partnership request to the company and can successfully redeem a voucher.

Scenario 2 – Unsuccessful Reward

The user throws the same piece of glass, but the recognition fails slightly.

The user ends up with negative points (-2.5).

When attempting to redeem a voucher, the system does not allow it because the loyalty points are insufficient.

Summary

This project demonstrates a simple yet interactive simulation of a smart recycling workflow. It shows:

How users interact with intelligent bins.

How loyalty points are earned and used.

How partnerships with companies can create incentives for recycling.

That systems can include randomness to simulate real-life imperfections.

It’s a clear example of how technology can encourage sustainable behavior while tracking user interactions in a controlled system.
