package de.schaeferd.fullstackapp.todo;

import org.jspecify.annotations.Nullable;

import java.time.Instant;

public record Todo(Long id,
                   String title,
                   String createdBy,
                   Instant createdAt,
                   @Nullable String completedBy,
                   @Nullable Instant completedAt)
{
}
