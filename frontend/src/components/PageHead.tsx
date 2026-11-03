import type { ReactNode } from 'react';
import { Icon, type IconName } from './Icon';

export function PageHead({ icon, crumb, title, children }: { icon: IconName; crumb?: string; title: string; children?: ReactNode }) {
  return (
    <div className="page-head">
      <div className="page-title">
        <span className="page-icon">
          <Icon name={icon} size={22} />
        </span>
        <div>
          {crumb && <div className="crumb">{crumb}</div>}
          <h1>{title}</h1>
        </div>
      </div>
      {children && <div className="page-actions">{children}</div>}
    </div>
  );
}

export function NewButton({ onClick, label = 'Nuevo' }: { onClick: () => void; label?: string }) {
  return (
    <button className="btn btn-accent" onClick={onClick}>
      <Icon name="plus" size={16} /> {label}
    </button>
  );
}
