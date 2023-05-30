package ge.carapp.carappapi.models.openai;

import java.util.List;

public record EventData(List<EventChoice> choices) {
}
