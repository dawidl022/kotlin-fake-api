package io.github.dawidl022.resolvers

class RecordNotFoundWithIdException(resourceName: String, id: Int) : Exception(
    "No $resourceName with id $id"
);

class FailedOperationException(operationName: String, resourceName: String, id: Int? = null): Exception(
    "Failed to $operationName $resourceName${if (id != null) " with id $id" else ""}"
)
