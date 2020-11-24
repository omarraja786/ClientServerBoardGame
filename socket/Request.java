package csgame.socket;

import java.util.Arrays;


public class Request {
    public RequestType type;
    public String[] params;

    public Request(RequestType type, String... params) {
        this.type = type;
        this.params = params;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        for (int i = 0; i < params.length; i++)
            sb.append(" " + params[i]);
        return sb.toString();
    }

    public static Request parse(String line) {
        try {
            String[] items = line.trim().split("\\s+");
            switch (items[0].toUpperCase()) {
                case "MOVE":
                    return new Request(RequestType.MOVE, items[1], items[2], items[3]);
                case "BOARD":
                    return new Request(RequestType.BOARD);
                case "COMMANDS":
                    return new Request(RequestType.COMMANDS);
                case "FREEDOM":
                    return new Request(RequestType.FREEDOM, items[1], items[2], items[3]);
                case "REPLACEMENT":
                    return new Request(RequestType.REPLACEMENT, items[1], items[2], items[3]);
                default:
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return new Request(RequestType.INVALID, line);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Request other = (Request) obj;
        if (!Arrays.equals(params, other.params))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
}
