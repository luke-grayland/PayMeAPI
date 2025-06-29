package com.LukeLabs.PayMeAPI.handlers;

import lombok.Getter;

public record SafeBetResult(boolean blockIsRequired, @Getter SafeBetHandler handler) {

}
