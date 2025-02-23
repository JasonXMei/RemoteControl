// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: 1.proto

package com.jason.use.protocol;

public final class WUProto {
  private WUProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface WUProtocolOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protocol.WUProtocol)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required int32 msgType = 1;</code>
     */
    boolean hasMsgType();
    /**
     * <code>required int32 msgType = 1;</code>
     */
    int getMsgType();

    /**
     * <code>required int32 sendUserId = 2;</code>
     */
    boolean hasSendUserId();
    /**
     * <code>required int32 sendUserId = 2;</code>
     */
    int getSendUserId();

    /**
     * <code>optional bytes screenImg = 3;</code>
     */
    boolean hasScreenImg();
    /**
     * <code>optional bytes screenImg = 3;</code>
     */
    com.google.protobuf.ByteString getScreenImg();

    /**
     * <code>optional bytes userEvent = 4;</code>
     */
    boolean hasUserEvent();
    /**
     * <code>optional bytes userEvent = 4;</code>
     */
    com.google.protobuf.ByteString getUserEvent();

    /**
     * <code>optional int32 receiveUserId = 5;</code>
     */
    boolean hasReceiveUserId();
    /**
     * <code>optional int32 receiveUserId = 5;</code>
     */
    int getReceiveUserId();

    /**
     * <code>optional string message = 6;</code>
     */
    boolean hasMessage();
    /**
     * <code>optional string message = 6;</code>
     */
    String getMessage();
    /**
     * <code>optional string message = 6;</code>
     */
    com.google.protobuf.ByteString
        getMessageBytes();
  }
  /**
   * Protobuf type {@code protocol.WUProtocol}
   */
  public  static final class WUProtocol extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protocol.WUProtocol)
      WUProtocolOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use WUProtocol.newBuilder() to construct.
    private WUProtocol(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private WUProtocol() {
      msgType_ = 0;
      sendUserId_ = 0;
      screenImg_ = com.google.protobuf.ByteString.EMPTY;
      userEvent_ = com.google.protobuf.ByteString.EMPTY;
      receiveUserId_ = 0;
      message_ = "";
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private WUProtocol(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              bitField0_ |= 0x00000001;
              msgType_ = input.readInt32();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              sendUserId_ = input.readInt32();
              break;
            }
            case 26: {
              bitField0_ |= 0x00000004;
              screenImg_ = input.readBytes();
              break;
            }
            case 34: {
              bitField0_ |= 0x00000008;
              userEvent_ = input.readBytes();
              break;
            }
            case 40: {
              bitField0_ |= 0x00000010;
              receiveUserId_ = input.readInt32();
              break;
            }
            case 50: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000020;
              message_ = bs;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return WUProto.internal_static_protocol_WUProtocol_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return WUProto.internal_static_protocol_WUProtocol_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              WUProtocol.class, Builder.class);
    }

    private int bitField0_;
    public static final int MSGTYPE_FIELD_NUMBER = 1;
    private int msgType_;
    /**
     * <code>required int32 msgType = 1;</code>
     */
    public boolean hasMsgType() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int32 msgType = 1;</code>
     */
    public int getMsgType() {
      return msgType_;
    }

    public static final int SENDUSERID_FIELD_NUMBER = 2;
    private int sendUserId_;
    /**
     * <code>required int32 sendUserId = 2;</code>
     */
    public boolean hasSendUserId() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required int32 sendUserId = 2;</code>
     */
    public int getSendUserId() {
      return sendUserId_;
    }

    public static final int SCREENIMG_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString screenImg_;
    /**
     * <code>optional bytes screenImg = 3;</code>
     */
    public boolean hasScreenImg() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional bytes screenImg = 3;</code>
     */
    public com.google.protobuf.ByteString getScreenImg() {
      return screenImg_;
    }

    public static final int USEREVENT_FIELD_NUMBER = 4;
    private com.google.protobuf.ByteString userEvent_;
    /**
     * <code>optional bytes userEvent = 4;</code>
     */
    public boolean hasUserEvent() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>optional bytes userEvent = 4;</code>
     */
    public com.google.protobuf.ByteString getUserEvent() {
      return userEvent_;
    }

    public static final int RECEIVEUSERID_FIELD_NUMBER = 5;
    private int receiveUserId_;
    /**
     * <code>optional int32 receiveUserId = 5;</code>
     */
    public boolean hasReceiveUserId() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>optional int32 receiveUserId = 5;</code>
     */
    public int getReceiveUserId() {
      return receiveUserId_;
    }

    public static final int MESSAGE_FIELD_NUMBER = 6;
    private volatile Object message_;
    /**
     * <code>optional string message = 6;</code>
     */
    public boolean hasMessage() {
      return ((bitField0_ & 0x00000020) == 0x00000020);
    }
    /**
     * <code>optional string message = 6;</code>
     */
    public String getMessage() {
      Object ref = message_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          message_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string message = 6;</code>
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      Object ref = message_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasMsgType()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSendUserId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, msgType_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt32(2, sendUserId_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, screenImg_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeBytes(4, userEvent_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        output.writeInt32(5, receiveUserId_);
      }
      if (((bitField0_ & 0x00000020) == 0x00000020)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, message_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, msgType_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, sendUserId_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, screenImg_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, userEvent_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(5, receiveUserId_);
      }
      if (((bitField0_ & 0x00000020) == 0x00000020)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, message_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof WUProtocol)) {
        return super.equals(obj);
      }
      WUProtocol other = (WUProtocol) obj;

      boolean result = true;
      result = result && (hasMsgType() == other.hasMsgType());
      if (hasMsgType()) {
        result = result && (getMsgType()
            == other.getMsgType());
      }
      result = result && (hasSendUserId() == other.hasSendUserId());
      if (hasSendUserId()) {
        result = result && (getSendUserId()
            == other.getSendUserId());
      }
      result = result && (hasScreenImg() == other.hasScreenImg());
      if (hasScreenImg()) {
        result = result && getScreenImg()
            .equals(other.getScreenImg());
      }
      result = result && (hasUserEvent() == other.hasUserEvent());
      if (hasUserEvent()) {
        result = result && getUserEvent()
            .equals(other.getUserEvent());
      }
      result = result && (hasReceiveUserId() == other.hasReceiveUserId());
      if (hasReceiveUserId()) {
        result = result && (getReceiveUserId()
            == other.getReceiveUserId());
      }
      result = result && (hasMessage() == other.hasMessage());
      if (hasMessage()) {
        result = result && getMessage()
            .equals(other.getMessage());
      }
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasMsgType()) {
        hash = (37 * hash) + MSGTYPE_FIELD_NUMBER;
        hash = (53 * hash) + getMsgType();
      }
      if (hasSendUserId()) {
        hash = (37 * hash) + SENDUSERID_FIELD_NUMBER;
        hash = (53 * hash) + getSendUserId();
      }
      if (hasScreenImg()) {
        hash = (37 * hash) + SCREENIMG_FIELD_NUMBER;
        hash = (53 * hash) + getScreenImg().hashCode();
      }
      if (hasUserEvent()) {
        hash = (37 * hash) + USEREVENT_FIELD_NUMBER;
        hash = (53 * hash) + getUserEvent().hashCode();
      }
      if (hasReceiveUserId()) {
        hash = (37 * hash) + RECEIVEUSERID_FIELD_NUMBER;
        hash = (53 * hash) + getReceiveUserId();
      }
      if (hasMessage()) {
        hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
        hash = (53 * hash) + getMessage().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static WUProtocol parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static WUProtocol parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static WUProtocol parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static WUProtocol parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static WUProtocol parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static WUProtocol parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static WUProtocol parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static WUProtocol parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static WUProtocol parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static WUProtocol parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static WUProtocol parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static WUProtocol parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(WUProtocol prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protocol.WUProtocol}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protocol.WUProtocol)
        WUProtocolOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return WUProto.internal_static_protocol_WUProtocol_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return WUProto.internal_static_protocol_WUProtocol_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                WUProtocol.class, Builder.class);
      }

      // Construct using com.jason.web.protocol.WUProto.WUProtocol.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        msgType_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        sendUserId_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        screenImg_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000004);
        userEvent_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        receiveUserId_ = 0;
        bitField0_ = (bitField0_ & ~0x00000010);
        message_ = "";
        bitField0_ = (bitField0_ & ~0x00000020);
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return WUProto.internal_static_protocol_WUProtocol_descriptor;
      }

      @Override
      public WUProtocol getDefaultInstanceForType() {
        return WUProtocol.getDefaultInstance();
      }

      @Override
      public WUProtocol build() {
        WUProtocol result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public WUProtocol buildPartial() {
        WUProtocol result = new WUProtocol(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.msgType_ = msgType_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.sendUserId_ = sendUserId_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.screenImg_ = screenImg_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.userEvent_ = userEvent_;
        if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
          to_bitField0_ |= 0x00000010;
        }
        result.receiveUserId_ = receiveUserId_;
        if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
          to_bitField0_ |= 0x00000020;
        }
        result.message_ = message_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return (Builder) super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof WUProtocol) {
          return mergeFrom((WUProtocol)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(WUProtocol other) {
        if (other == WUProtocol.getDefaultInstance()) return this;
        if (other.hasMsgType()) {
          setMsgType(other.getMsgType());
        }
        if (other.hasSendUserId()) {
          setSendUserId(other.getSendUserId());
        }
        if (other.hasScreenImg()) {
          setScreenImg(other.getScreenImg());
        }
        if (other.hasUserEvent()) {
          setUserEvent(other.getUserEvent());
        }
        if (other.hasReceiveUserId()) {
          setReceiveUserId(other.getReceiveUserId());
        }
        if (other.hasMessage()) {
          bitField0_ |= 0x00000020;
          message_ = other.message_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        if (!hasMsgType()) {
          return false;
        }
        if (!hasSendUserId()) {
          return false;
        }
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        WUProtocol parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (WUProtocol) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int msgType_ ;
      /**
       * <code>required int32 msgType = 1;</code>
       */
      public boolean hasMsgType() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int32 msgType = 1;</code>
       */
      public int getMsgType() {
        return msgType_;
      }
      /**
       * <code>required int32 msgType = 1;</code>
       */
      public Builder setMsgType(int value) {
        bitField0_ |= 0x00000001;
        msgType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 msgType = 1;</code>
       */
      public Builder clearMsgType() {
        bitField0_ = (bitField0_ & ~0x00000001);
        msgType_ = 0;
        onChanged();
        return this;
      }

      private int sendUserId_ ;
      /**
       * <code>required int32 sendUserId = 2;</code>
       */
      public boolean hasSendUserId() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required int32 sendUserId = 2;</code>
       */
      public int getSendUserId() {
        return sendUserId_;
      }
      /**
       * <code>required int32 sendUserId = 2;</code>
       */
      public Builder setSendUserId(int value) {
        bitField0_ |= 0x00000002;
        sendUserId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 sendUserId = 2;</code>
       */
      public Builder clearSendUserId() {
        bitField0_ = (bitField0_ & ~0x00000002);
        sendUserId_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString screenImg_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes screenImg = 3;</code>
       */
      public boolean hasScreenImg() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional bytes screenImg = 3;</code>
       */
      public com.google.protobuf.ByteString getScreenImg() {
        return screenImg_;
      }
      /**
       * <code>optional bytes screenImg = 3;</code>
       */
      public Builder setScreenImg(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        screenImg_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes screenImg = 3;</code>
       */
      public Builder clearScreenImg() {
        bitField0_ = (bitField0_ & ~0x00000004);
        screenImg_ = getDefaultInstance().getScreenImg();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString userEvent_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes userEvent = 4;</code>
       */
      public boolean hasUserEvent() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>optional bytes userEvent = 4;</code>
       */
      public com.google.protobuf.ByteString getUserEvent() {
        return userEvent_;
      }
      /**
       * <code>optional bytes userEvent = 4;</code>
       */
      public Builder setUserEvent(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        userEvent_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes userEvent = 4;</code>
       */
      public Builder clearUserEvent() {
        bitField0_ = (bitField0_ & ~0x00000008);
        userEvent_ = getDefaultInstance().getUserEvent();
        onChanged();
        return this;
      }

      private int receiveUserId_ ;
      /**
       * <code>optional int32 receiveUserId = 5;</code>
       */
      public boolean hasReceiveUserId() {
        return ((bitField0_ & 0x00000010) == 0x00000010);
      }
      /**
       * <code>optional int32 receiveUserId = 5;</code>
       */
      public int getReceiveUserId() {
        return receiveUserId_;
      }
      /**
       * <code>optional int32 receiveUserId = 5;</code>
       */
      public Builder setReceiveUserId(int value) {
        bitField0_ |= 0x00000010;
        receiveUserId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 receiveUserId = 5;</code>
       */
      public Builder clearReceiveUserId() {
        bitField0_ = (bitField0_ & ~0x00000010);
        receiveUserId_ = 0;
        onChanged();
        return this;
      }

      private Object message_ = "";
      /**
       * <code>optional string message = 6;</code>
       */
      public boolean hasMessage() {
        return ((bitField0_ & 0x00000020) == 0x00000020);
      }
      /**
       * <code>optional string message = 6;</code>
       */
      public String getMessage() {
        Object ref = message_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            message_ = s;
          }
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>optional string message = 6;</code>
       */
      public com.google.protobuf.ByteString
          getMessageBytes() {
        Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string message = 6;</code>
       */
      public Builder setMessage(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000020;
        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string message = 6;</code>
       */
      public Builder clearMessage() {
        bitField0_ = (bitField0_ & ~0x00000020);
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }
      /**
       * <code>optional string message = 6;</code>
       */
      public Builder setMessageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000020;
        message_ = value;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:protocol.WUProtocol)
    }

    // @@protoc_insertion_point(class_scope:protocol.WUProtocol)
    private static final WUProtocol DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new WUProtocol();
    }

    public static WUProtocol getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @Deprecated public static final com.google.protobuf.Parser<WUProtocol>
        PARSER = new com.google.protobuf.AbstractParser<WUProtocol>() {
      @Override
      public WUProtocol parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new WUProtocol(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<WUProtocol> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<WUProtocol> getParserForType() {
      return PARSER;
    }

    @Override
    public WUProtocol getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_WUProtocol_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protocol_WUProtocol_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\0071.proto\022\010protocol\"\177\n\nWUProtocol\022\017\n\007msg" +
      "Type\030\001 \002(\005\022\022\n\nsendUserId\030\002 \002(\005\022\021\n\tscreen" +
      "Img\030\003 \001(\014\022\021\n\tuserEvent\030\004 \001(\014\022\025\n\rreceiveU" +
      "serId\030\005 \001(\005\022\017\n\007message\030\006 \001(\tB!\n\026com.jaso" +
      "n.web.protocolB\007WUProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_protocol_WUProtocol_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protocol_WUProtocol_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protocol_WUProtocol_descriptor,
        new String[] { "MsgType", "SendUserId", "ScreenImg", "UserEvent", "ReceiveUserId", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
