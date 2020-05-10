package view;

import network.messages.MessageTarget;

/**
 * Defines a common interface for all Client-Side Views
 * Client side Views are the targets of all message sent from
 * the server using the @link{MessageToView} interface
 */
public interface ClientView extends View, MessageTarget{

}
