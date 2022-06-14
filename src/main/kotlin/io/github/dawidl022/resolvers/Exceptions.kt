package io.github.dawidl022.resolvers

class RecordNotFoundWithIdException(resourceName: String, id: Int) : Exception(
    "No $resourceName with id $id"
);
