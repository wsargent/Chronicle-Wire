package net.openhft.chronicle.wire;

import net.openhft.chronicle.bytes.Bytes;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * @author ryanlea
 */
public class WireBug35Test extends WireTestCommon {

    @Test
    public void objectsInSequence() {
        final Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();

        final Wire wire = WireType.TEXT.apply(bytes);
        wire.write(() -> "seq").sequence(seq -> {
            seq.marshallable(obj -> obj.write(() -> "key").text("value"));
            seq.marshallable(obj -> obj.write(() -> "key").text("value"));
        });

        @NotNull final String text = wire.asText().toString();
        Object load = new Yaml().load(text);

        assertEquals("{seq=[{key=value}, {key=value}]}", load.toString());

        bytes.releaseLast();
    }

    @Test
    public void objectsInSequenceBinaryWire() {
        final Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        final Wire wire = WireType.BINARY.apply(bytes);
        wire.write(() -> "seq").sequence(seq -> {
            seq.marshallable(obj -> obj.write(() -> "key").text("value"));
            seq.marshallable(obj -> obj.write(() -> "key").text("value"));
        });

        @NotNull final String text = wire.asText().toString();
        Object load = new Yaml().load(text);

        assertEquals("{seq=[{key=value}, {key=value}]}", load.toString());

        bytes.releaseLast();
    }
}