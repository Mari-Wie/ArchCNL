/**
 * generated by Xtext 2.22.0
 */
package org.architecture.cnl.archcnl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fact Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.architecture.cnl.archcnl.FactStatement#getAssertion <em>Assertion</em>}</li>
 * </ul>
 *
 * @see org.architecture.cnl.archcnl.ArchcnlPackage#getFactStatement()
 * @model
 * @generated
 */
public interface FactStatement extends EObject
{
  /**
   * Returns the value of the '<em><b>Assertion</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assertion</em>' containment reference.
   * @see #setAssertion(EObject)
   * @see org.architecture.cnl.archcnl.ArchcnlPackage#getFactStatement_Assertion()
   * @model containment="true"
   * @generated
   */
  EObject getAssertion();

  /**
   * Sets the value of the '{@link org.architecture.cnl.archcnl.FactStatement#getAssertion <em>Assertion</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Assertion</em>' containment reference.
   * @see #getAssertion()
   * @generated
   */
  void setAssertion(EObject value);

} // FactStatement
