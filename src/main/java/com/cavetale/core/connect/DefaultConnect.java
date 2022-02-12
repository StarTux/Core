package com.cavetale.core.connect;

final class DefaultConnect implements Connect {
    protected static final DefaultConnect INSTANCE = new DefaultConnect();

    @Override
    public String getServerName() {
        return "cavetale";
    }
}
