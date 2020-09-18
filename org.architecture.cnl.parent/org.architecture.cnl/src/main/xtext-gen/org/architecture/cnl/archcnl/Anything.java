/**
 * generated by Xtext 2.22.0
 */
package org.architecture.cnl.archcnl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Anything</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.architecture.cnl.archcnl.Anything#getRelation <em>Relation</em>}</li>
 * </ul>
 *
 * @see org.architecture.cnl.archcnl.ArchcnlPackage#getAnything()
 * @model
 * @generated
 */
public interface Anything extends EObject
{
  /**
   * Returns the value of the '<em><b>Relation</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relation</em>' containment reference.
   * @see #setRelation(Relation)
   * @see org.architecture.cnl.archcnl.ArchcnlPackage#getAnything_Relation()
   * @model containment="true"
   * @generated
   */
  Relation getRelation();

  /**
   * Sets the value of the '{@link org.architecture.cnl.archcnl.Anything#getRelation <em>Relation</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Relation</em>' containment reference.
   * @see #getRelation()
   * @generated
   */
  void setRelation(Relation value);

} // Anything
