package com.fetch.receipt_processor_challenge.transformers

interface Transformer<I,O> {
    fun transform(input: I): O
}