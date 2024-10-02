package com.fetch.receipt_processor_challenge.transformers

import com.fetch.receipt_processor_challenge.dto.Points
import com.fetch.receipt_processor_challenge.dto.Receipt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.math.floor

val LOG: Logger = LoggerFactory.getLogger(PointsTransformer::class.java)

@Component
class PointsTransformer: Transformer<Receipt, Points> {

    /**
     * Transform a [Receipt] into [Points] by calculating the number of points
     * a receipt is worth.
     */
    override fun transform(input: Receipt): Points {
        var totalPoints = 0.00
        // iterate each character in retailer name an increment if character is alphanumeric
        input.retailer.toCharArray().forEach { char -> if (char.isLetter() || char.isDigit()) totalPoints++ }
        LOG.debug("Added points for alphanumberic: {}", totalPoints)

        // If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
        input.items.forEach { item ->
            if (item.shortDescription.trimEnd().length % 3 == 0) {
                val reducedPrice = item.price.toDouble() * 0.2
                totalPoints += if (reducedPrice % 1 == 0.00) {
                    reducedPrice
                } else {
                    floor(reducedPrice + 1)
                }
                LOG.debug("Added points for item description: {}", totalPoints)
            }
        }

        // 50 points if the total is a round dollar amount with no cents.
        if (input.total.toDouble() % 1 == 0.00) {
            totalPoints += 50
            LOG.debug("Added points for round dollar: {}", totalPoints)
        }

        // 25 points if the total is a multiple of 0.25
        if (input.total.toDouble() % 0.25 == 0.00) {
            totalPoints += 25
            LOG.debug("Added points for multiple of 0.25: {}", totalPoints)
        }

        // 5 points for every two items on the receipt.
        totalPoints += (input.items.size / 2) * 5
        LOG.debug("Added points for every two items: {}", totalPoints)

        // 6 points if the day in the purchase date is odd.
        if (input.purchaseDate.split('-')[2].toInt() % 2 != 0) {
            totalPoints += 6
            LOG.debug("Added points for odd date: {}", totalPoints)
        }

        // 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        val dateSplit = input.purchaseTime.split(':')
        if ((dateSplit[0].toInt() >= 14 && dateSplit[1].toInt() > 0)
            && dateSplit[0].toInt() < 16) {
            totalPoints += 10
            LOG.debug("Added points for time between 2pm and 4pm: {}", totalPoints)
        }
        LOG.debug("Total points: {}", totalPoints)

        return Points(input.id!!, totalPoints)
    }
}