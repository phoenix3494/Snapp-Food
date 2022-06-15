package model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    NotAccepted,
    Accepted,
    Delivered,
}
