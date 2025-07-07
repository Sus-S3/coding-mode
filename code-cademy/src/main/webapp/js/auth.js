class AuthManager {
  constructor() {
    this.currentUser = null;
    this.init();
  }

  async init() {
    // Verificar estado de sesión del servidor
    await this.checkSessionStatus();
  }

  async checkSessionStatus() {
    try {
      const response = await fetch('session-status');
      const data = await response.json();
      
      if (data.authenticated) {
        this.currentUser = {
          id: data.id,
          nombre: data.nombre,
          email: data.email,
          rol: data.rol,
          isAdmin: data.isAdmin
        };
        this.updateNavigation();
      } else {
        this.currentUser = null;
        this.updateNavigation();
      }
    } catch (error) {
      console.error('Error checking session status:', error);
      this.currentUser = null;
      this.updateNavigation();
    }
  }

  updateNavigation() {
    const navAuth = document.getElementById('navAuth');
    
    if (!navAuth) {
      return;
    }

    if (this.currentUser) {
      // Usuario autenticado
      navAuth.innerHTML = `
        <div class="user-menu">
          <span class="user-name">Hola, ${this.currentUser.nombre}</span>
          <div class="user-dropdown">
            <a href="profile.jsp" class="dropdown-item">Mi Perfil</a>
            ${this.currentUser.isAdmin ? '<a href="dashboard" class="dropdown-item">Dashboard</a>' : ''}
            <button onclick="Auth.logout()" class="dropdown-item">Cerrar Sesión</button>
          </div>
        </div>
      `;
    } else {
      // Usuario no autenticado
      navAuth.innerHTML = `
        <a href="login.jsp" class="btn btn-ghost">Iniciar Sesión</a>
        <a href="register.jsp" class="btn btn-primary">Registrarse</a>
      `;
    }
  }

  async login(email, password) {
    try {
      const formData = new URLSearchParams();
      formData.append('email', email);
      formData.append('password', password);
      
      const response = await fetch('login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString()
      });
      
      const data = await response.json();
      
      if (data.success) {
        // Actualizar estado de sesión
        await this.checkSessionStatus();
        return { success: true, redirect: data.redirect };
      } else {
        return { success: false, message: data.message };
      }
    } catch (error) {
      console.error('Error during login:', error);
      return { success: false, message: 'Error de conexión' };
    }
  }

  async register(userData) {
    try {
      const formData = new URLSearchParams();
      formData.append('nombre', userData.nombre);
      formData.append('email', userData.email);
      formData.append('telefono', userData.telefono);
      formData.append('pais', userData.pais);
      formData.append('password', userData.password);
      
      const response = await fetch('register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString()
      });
      
      const data = await response.json();
      
      if (data.success) {
        // Actualizar estado de sesión
        await this.checkSessionStatus();
        return { success: true, redirect: data.redirect };
      } else {
        return { success: false, message: data.message };
      }
    } catch (error) {
      console.error('Error during registration:', error);
      return { success: false, message: 'Error de conexión' };
    }
  }

  async logout() {
    try {
      const response = await fetch('logout', {
        method: 'POST'
      });
      
      const data = await response.json();
      
      if (data.success) {
        this.currentUser = null;
        this.updateNavigation();
        // Redirigir al index
        window.location.href = 'index.jsp';
      }
    } catch (error) {
      console.error('Error during logout:', error);
      // Forzar logout local
      this.currentUser = null;
      this.updateNavigation();
      window.location.href = 'index.jsp';
    }
  }

  getCurrentUser() {
    return this.currentUser;
  }

  isAuthenticated() {
    return this.currentUser !== null;
  }

  isAdmin() {
    return this.currentUser && this.currentUser.isAdmin;
  }
}

// Instancia global
const Auth = new AuthManager();

// Verificar sesión al cargar la página
document.addEventListener('DOMContentLoaded', () => {
  Auth.checkSessionStatus();
});
